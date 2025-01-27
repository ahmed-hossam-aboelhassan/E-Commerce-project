package com.ecommerceproject.Exception;

public class NotAnAvailableProductException extends RuntimeException {
    public NotAnAvailableProductException(String message) {
        super(message);
    }
}
