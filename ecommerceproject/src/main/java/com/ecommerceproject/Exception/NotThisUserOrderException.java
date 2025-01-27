package com.ecommerceproject.Exception;

public class NotThisUserOrderException extends RuntimeException {
    public NotThisUserOrderException(String message) {
        super(message);
    }
}
