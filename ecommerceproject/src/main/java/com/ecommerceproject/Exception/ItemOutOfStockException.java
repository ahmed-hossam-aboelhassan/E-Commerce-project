package com.ecommerceproject.Exception;

public class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException(String message) {
        super(message);
    }
}
