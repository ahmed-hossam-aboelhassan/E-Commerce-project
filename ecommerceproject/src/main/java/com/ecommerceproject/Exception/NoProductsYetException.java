package com.ecommerceproject.Exception;

public class NoProductsYetException extends RuntimeException {
    public NoProductsYetException(String message) {
        super(message);
    }
}