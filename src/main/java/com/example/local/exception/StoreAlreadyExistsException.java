package com.example.local.exception;

public class StoreAlreadyExistsException extends RuntimeException{
    public StoreAlreadyExistsException(String message){
        super(message);
    }
}
