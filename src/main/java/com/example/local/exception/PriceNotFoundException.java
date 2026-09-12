package com.example.local.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message){
        super(message);
    }

}
