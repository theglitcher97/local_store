package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailExceptionsHandler {
    @ExceptionHandler(exception = {EmailAlreadyExistsException.class})
    public ResponseEntity<GlobalExceptionResponse> emailAlreadyExists(EmailAlreadyExistsException e) {
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), "404"), HttpStatus.BAD_REQUEST);
    }
}
