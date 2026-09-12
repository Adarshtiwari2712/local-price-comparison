package com.example.local.exception;

public class StoreHasPricesException extends RuntimeException {

    public StoreHasPricesException(String message) {
        super(message);
    }
}