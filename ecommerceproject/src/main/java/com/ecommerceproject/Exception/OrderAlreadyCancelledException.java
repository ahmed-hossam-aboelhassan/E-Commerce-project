package com.ecommerceproject.Exception;

public class OrderAlreadyCancelledException extends RuntimeException {
    public OrderAlreadyCancelledException(String message) {
        super(message);
    }
}
