package com.ecommerceproject.Exception;

public class IncorrectEmailException extends RuntimeException {
    public IncorrectEmailException(String message) {
        super(message);
    }
}
