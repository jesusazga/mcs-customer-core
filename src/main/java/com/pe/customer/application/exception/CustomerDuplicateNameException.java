package com.pe.customer.application.exception;

public class CustomerDuplicateNameException extends RuntimeException {

	public CustomerDuplicateNameException(String message) {
        super(message);
    }
}
