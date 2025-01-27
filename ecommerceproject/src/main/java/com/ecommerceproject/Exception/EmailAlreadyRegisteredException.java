package com.ecommerceproject.Exception;

public class EmailAlreadyRegisteredException extends RuntimeException{
    public EmailAlreadyRegisteredException(String string){
        super(string);
    }
}
