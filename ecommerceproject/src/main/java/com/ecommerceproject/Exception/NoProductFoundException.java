package com.ecommerceproject.Exception;

public class NoProductFoundException extends RuntimeException {
    public NoProductFoundException(String message) {
        super(message);
    }
}
