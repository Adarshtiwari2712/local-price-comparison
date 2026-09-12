package com.example.local.exception;

public class ProductHasPricesException extends RuntimeException{
    public ProductHasPricesException(String message){
        super(message);
    }
}
