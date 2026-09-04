package com.example.local.exception;

public class StoreNotFoundException extends RuntimeException{

    public StoreNotFoundException(String message){
        super(message);
    }
}
