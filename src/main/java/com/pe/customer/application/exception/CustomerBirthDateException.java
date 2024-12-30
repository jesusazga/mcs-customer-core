package com.pe.customer.application.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomerBirthDateException extends RuntimeException {

private Map<String, String> errors;
	
	public CustomerBirthDateException(String message) {
		super(message);
		errors = new HashMap<>();
        errors.put("birthDate", message);
    }
	
	public Map<String, String> getErrors() {
        return errors;
    }
}
