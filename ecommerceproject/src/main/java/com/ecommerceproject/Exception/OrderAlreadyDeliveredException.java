package com.ecommerceproject.Exception;

public class OrderAlreadyDeliveredException extends RuntimeException {
    public OrderAlreadyDeliveredException(String message) {
        super(message);
    }
}
