package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.InvalidOrderStateException;
import com.store.local_store.web.exceptions.custom.InvalidReservationException;
import com.store.local_store.web.exceptions.custom.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionsHandler {
    @ExceptionHandler(exception = {InvalidOrderStateException.class})
    public ResponseEntity<GlobalExceptionResponse> invalidOrderState(InvalidOrderStateException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(exception = {InvalidReservationException.class})
    public ResponseEntity<GlobalExceptionResponse> invalidReservation(InvalidReservationException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(exception = {OrderNotFoundException.class})
    public ResponseEntity<GlobalExceptionResponse> orderNotFound(OrderNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), HttpStatus.NOT_FOUND.toString()),
                HttpStatus.NOT_FOUND);
    }
}
