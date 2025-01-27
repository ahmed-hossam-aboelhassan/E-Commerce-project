package com.ecommerceproject.Exception;

public class ItemNotInCartException extends RuntimeException {
    public ItemNotInCartException(String message) {
        super(message);
    }
}
