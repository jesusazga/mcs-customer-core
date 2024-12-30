package com.pe.customer.application.service;

import java.util.List;

import com.pe.customer.application.dto.CustomerResponse;
import com.pe.customer.application.dto.MetricsResponse;
import com.pe.customer.domain.model.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);
	MetricsResponse getCustomerMetrics();
	List<CustomerResponse> getAllCustomers();
}
