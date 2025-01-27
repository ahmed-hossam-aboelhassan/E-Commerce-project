package com.ecommerceproject.Exception;

public class NoOrdersYetException extends RuntimeException {
    public NoOrdersYetException(String message) {
        super(message);
    }
}
