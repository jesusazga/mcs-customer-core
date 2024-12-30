package com.pe.customer.application.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomerAgeNoValidException extends RuntimeException {

	private Map<String, String> errors;
	
	public CustomerAgeNoValidException(String message) {
		super(message);
		errors = new HashMap<>();
        errors.put("age", message);
    }
	
	public Map<String, String> getErrors() {
        return errors;
    }
}
