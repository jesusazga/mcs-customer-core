package com.pe.customer.application.exception;

public class CustomerNotFoundException extends RuntimeException {

	public CustomerNotFoundException(String message) {
        super(message);
    }
}
