package com.ecommerceproject.Exception;

public class NoAvailableAddressesException extends RuntimeException {
    public NoAvailableAddressesException(String message) {
        super(message);
    }
}
