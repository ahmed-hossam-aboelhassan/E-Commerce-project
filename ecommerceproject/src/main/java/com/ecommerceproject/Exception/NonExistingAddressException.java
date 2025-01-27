package com.ecommerceproject.Exception;

public class NonExistingAddressException extends RuntimeException {
    public NonExistingAddressException(String message) {
        super(message);
    }
}
