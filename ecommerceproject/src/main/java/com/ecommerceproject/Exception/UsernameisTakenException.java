package com.ecommerceproject.Exception;

public class UsernameisTakenException extends RuntimeException {
    public UsernameisTakenException(String message) {
        super(message);
    }
}
