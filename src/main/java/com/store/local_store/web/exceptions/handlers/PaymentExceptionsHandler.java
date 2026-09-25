package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.PaymentNotAllowedException;
import com.store.local_store.web.exceptions.custom.PaymentStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentExceptionsHandler {
    @ExceptionHandler(exception = {PaymentNotAllowedException.class})
    public ResponseEntity<GlobalExceptionResponse> paymentNotAllowed(PaymentNotAllowedException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = {PaymentStatusException.class})
    public ResponseEntity<GlobalExceptionResponse> paymentStatus(PaymentStatusException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.toString()),
                HttpStatus.NOT_ACCEPTABLE);
    }
}
