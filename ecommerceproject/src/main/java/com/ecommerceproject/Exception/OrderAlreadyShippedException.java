package com.ecommerceproject.Exception;

public class OrderAlreadyShippedException extends RuntimeException {
    public OrderAlreadyShippedException(String message) {
        super(message);
    }
}
