package com.ecommerceproject.Exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
