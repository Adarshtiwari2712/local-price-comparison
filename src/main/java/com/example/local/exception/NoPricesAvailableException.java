package com.example.local.exception;

public class NoPricesAvailableException extends RuntimeException {
    public NoPricesAvailableException(String message){
        super(message);
    }
}

