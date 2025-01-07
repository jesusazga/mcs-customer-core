package com.pe.customer.infrastructure.adapter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pe.customer.application.dto.CustomerResponse;
import com.pe.customer.application.dto.MetricsResponse;
import com.pe.customer.application.service.CustomerService;
import com.pe.customer.domain.model.Customer;
import com.pe.customer.util.ConstantSwagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Crear un nuevo cliente", description = "Permite crear un cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente creado satisfactoriamente"),
			@ApiResponse(responseCode = "400", description = "Validación de errores de campos", content = @Content(mediaType = "application/json", schema = @Schema(example = ConstantSwagger.VALIDATION_ERROR_400_BAD_REQ))),
			@ApiResponse(responseCode = "401", description = "Error de Autorización", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))),
			@ApiResponse(responseCode = "422", description = "Validación correcta pero no se puede registrar", content = @Content(mediaType = "application/json", schema = @Schema(example = ConstantSwagger.VALIDATION_ERROR_422_DUPLICATE_NAME))) })
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
		Customer createdCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
	}

	@Operation(summary = "Obtener métricas de los clientes", description = "Devuelve métricas como el promedio de edad y la desviación estándar de las edades de los clientes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Métricas obtenidas exitosamente"),
			@ApiResponse(responseCode = "401", description = "Error de Autorización", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))),
			@ApiResponse(responseCode = "422", description = "Consulta exitosa pero no se pudo calcular las métricas", content = @Content(mediaType = "application/json", schema = @Schema(example = ConstantSwagger.VALIDATION_ERROR_422_METRICS))) })
	@GetMapping("/metrics")
	public ResponseEntity<MetricsResponse> getCustomerMetrics() {
		MetricsResponse metrics = customerService.getCustomerMetrics();
		return ResponseEntity.ok(metrics);
	}

	@Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de todos los clientes registrados")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes obtenido satisfactoriamente"),
			@ApiResponse(responseCode = "401", description = "Error de Autorización", content = @Content(mediaType = "application/json", schema = @Schema(example = ""))) })
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
		return ResponseEntity.ok(customerService.getAllCustomers());
	}
}
