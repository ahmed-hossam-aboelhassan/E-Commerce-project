package com.ecommerceproject.Exception;

public class NoAvailableOrdersException extends RuntimeException {
    public NoAvailableOrdersException(String message) {
        super(message);
    }
}
