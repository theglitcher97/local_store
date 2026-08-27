package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.EmptyCartException;
import com.store.local_store.web.exceptions.custom.InsufficientStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler(exception = {EmptyCartException.class})
    public ResponseEntity<GlobalExceptionResponse> emptyCartExceptionHandler(EmptyCartException e){
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), "406"), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(exception = {InsufficientStockException.class})
    public ResponseEntity<GlobalExceptionResponse> insufficientStockExceptionHandler(InsufficientStockException e){
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), "406"), HttpStatus.NOT_ACCEPTABLE);
    }
}
