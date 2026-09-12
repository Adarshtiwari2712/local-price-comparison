package com.example.local.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProductAlreadyExists(ProductAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFound(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleStoreNotFound(StoreNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoPricesAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoPricesAvailable(NoPricesAvailableException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidCredentials(InvalidCredentialsException ex){
        return ex.getMessage();
    }

@ExceptionHandler(StoreAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleStoreAlreadyExists(StoreAlreadyExistsException ex){
        return ex.getMessage();
}
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePriceNotFound(PriceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ProductHasPricesException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleProductHasPrices(ProductHasPricesException ex){
        return ex.getMessage();
    }
    @ExceptionHandler(StoreHasPricesException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleStoreHasPrices(StoreHasPricesException ex){
        return ex.getMessage();

    }
}