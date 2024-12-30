package com.pe.customer.application.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pe.customer.application.dto.CustomerResponse;
import com.pe.customer.application.dto.MetricsResponse;
import com.pe.customer.application.exception.CustomerAgeNoValidException;
import com.pe.customer.application.exception.CustomerBirthDateException;
import com.pe.customer.application.exception.CustomerDuplicateNameException;
import com.pe.customer.application.exception.CustomerNotFoundException;
import com.pe.customer.application.exception.DatabaseConnectionException;
import com.pe.customer.application.service.CustomerService;
import com.pe.customer.domain.model.Customer;
import com.pe.customer.infrastructure.repository.CustomerRepository;
import com.pe.customer.util.SetValueUtil;
import com.pe.customer.util.ValidatorUtil;
import com.pe.customer.util.ZodiacSignCalculator;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	private static final String CUSTOMERS_NO_FOUND_MESSAGE = "No se encontraron clientes para calcular métricas.";
	private static final String CUSTOMERS_AGE_NO_VALID_MESSAGE = "La edad proporcionada no coincide con la fecha de nacimiento.";
	private static final String CUSTOMERS_DATE_NO_VALID_MESSAGE = "La fecha de nacimiento no es válida.";
	private static final String CUSTOMERS_BIRTHDATE_NO_VALID_MESSAGE = "La fecha de nacimiento no puede ser una fecha futura.";
	private static final String CUSTOMER_DUPLICATE_NAME_MESSAGE = "Ya existe un cliente con el mismo nombre.";
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ZodiacSignCalculator zodiacSignCalculator;
	
	@Override
	public Customer createCustomer(Customer customer) {
		logger.info("Creating a new customer: {} {}", customer.getName(), customer.getLastName());
		try {
			validateBirthDateField(customer.getBirthDate());
			validateAgeField(customer);
			validateDuplicateName(customer.getName());
			return customerRepository.save(customer);
		} catch (DataAccessException e) {
			throw new DatabaseConnectionException("Error al registrar con la base de datos", e);
		}
		
	}
	
	private void validateAgeField(Customer customer) {
		int age = calculateAge(customer.getBirthDate());
		if (customer.getAge() != age) {
            throw new CustomerAgeNoValidException(CUSTOMERS_AGE_NO_VALID_MESSAGE);
        }
	}
	
	private int calculateAge(String birthDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDateLocal = LocalDate.parse(birthDate, formatter);
		LocalDate currentDate = LocalDate.now();
		long yearsBetween = ChronoUnit.YEARS.between(birthDateLocal, currentDate);
		return (int) yearsBetween;
	}
	
	private void validateDuplicateName(String name) {
        boolean exists = customerRepository.existsByName(name);
        if (exists) {
            throw new CustomerDuplicateNameException(CUSTOMER_DUPLICATE_NAME_MESSAGE);
        }
    }
	
	private void validateBirthDateField(String birthDate) {
            
        if (!ValidatorUtil.validateDate(birthDate)) {
        	 throw new CustomerBirthDateException(CUSTOMERS_DATE_NO_VALID_MESSAGE);
        }
        if (ValidatorUtil.validIfIsAfter(birthDate)) {
            throw new CustomerBirthDateException(CUSTOMERS_BIRTHDATE_NO_VALID_MESSAGE);
        }
    }
	
	@Override
	public MetricsResponse getCustomerMetrics() {
		logger.info("Calculating all customer metrics");
		try {
			List<Customer> customers = customerRepository.findAll();
			
			if (customers.isEmpty()) {
				logger.error("No se encontraron clientes");
	            throw new CustomerNotFoundException(CUSTOMERS_NO_FOUND_MESSAGE);
	        }
			
			double averageAge = SetValueUtil.convertTwoDecimal(calculateAverageAge(customers));
	        
	        double standardDeviation = SetValueUtil.convertTwoDecimal(calculateStandardDeviation(customers, averageAge));
	        
	        logger.info("Calculated metrics: Average Age = {}, Standard Deviation = {}", averageAge, standardDeviation);
	        
	        return new MetricsResponse(averageAge, standardDeviation);
	        
		} catch (DataAccessException e) {
            throw new DatabaseConnectionException("Error al obtener las métricas de clientes desde la base de datos", e);
        }
	}
	
	private double calculateAverageAge(List<Customer> customers) {
        return customers.stream()
            .mapToInt(Customer::getAge)
            .average()
            .orElse(0);
    }

    private double calculateStandardDeviation(List<Customer> customers, double average) {
        double variance = customers.stream()
            .mapToDouble(c -> Math.pow(c.getAge() - average, 2))
            .average()
            .orElse(0);
        return Math.sqrt(variance);
    }

	@Override
	public List<CustomerResponse> getAllCustomers() {
		logger.info("Listando todos los clientes");
		try {
			return customerRepository.findAll().stream()
		            .map(this::mapToCustomerResponse)
		            .collect(Collectors.toList());
		
		} catch (DataAccessException e) {
	        throw new DatabaseConnectionException("Error al obtener los clientes desde la base de datos", e);
	    }
	}
	
	private CustomerResponse mapToCustomerResponse(Customer customer) {
		LocalDate dateOfBirth = LocalDate.parse(customer.getBirthDate(), DateTimeFormatter.ISO_DATE);
		String zodiacSign = zodiacSignCalculator.calculateZodiacSign(dateOfBirth);
		return CustomerResponse.builder()
	            .id(customer.getId())
	            .name(customer.getName())
	            .lastName(customer.getLastName())
	            .age(customer.getAge())
	            .birthDate(customer.getBirthDate())
	            .zodiacSign(zodiacSign)
	            .build();
    }
}
