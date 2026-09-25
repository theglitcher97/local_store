package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.EmptyCartException;
import com.store.local_store.web.exceptions.custom.InsufficientStockException;
import com.store.local_store.web.exceptions.custom.InvalidCartStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler(exception = {EmptyCartException.class})
    public ResponseEntity<GlobalExceptionResponse> emptyCart(EmptyCartException e){
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.toString()),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(exception = {InsufficientStockException.class})
    public ResponseEntity<GlobalExceptionResponse> insufficientStock(InsufficientStockException e){
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.toString()),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(exception = {InvalidCartStateException.class})
    public ResponseEntity<GlobalExceptionResponse> invalidCartState(InvalidCartStateException e){
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.toString()),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
