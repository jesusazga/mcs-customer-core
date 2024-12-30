package com.pe.customer.infrastructure.adapter.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pe.customer.application.exception.CustomerAgeNoValidException;
import com.pe.customer.application.exception.CustomerBirthDateException;
import com.pe.customer.application.exception.CustomerNotFoundException;
import com.pe.customer.application.exception.DatabaseConnectionException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,
	        WebRequest request) {

	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage()));

	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomerBirthDateException.class)
    public ResponseEntity<Map<String, String>> handleCustomerAgeNoValidException(CustomerBirthDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
    }
	
	@ExceptionHandler(CustomerAgeNoValidException.class)
    public ResponseEntity<Map<String, String>> handleCustomerAgeNoValidException(CustomerAgeNoValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<String> handleDatabaseConnectionException(DatabaseConnectionException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de Base de Datos: " + ex.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
