package com.ecommerceproject.Exception;

public class NonExistingStatusException extends RuntimeException {
    public NonExistingStatusException(String message) {
        super(message);
    }
}
