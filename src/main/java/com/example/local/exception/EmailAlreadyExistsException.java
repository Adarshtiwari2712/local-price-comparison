package com.example.local.exception;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
