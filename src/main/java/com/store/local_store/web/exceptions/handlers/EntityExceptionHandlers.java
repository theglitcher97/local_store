package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import com.store.local_store.web.exceptions.custom.IncorrectPasswordException;
import com.store.local_store.web.exceptions.custom.PasswordDontMatchException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandlers {

    @ExceptionHandler(exception = {EntityNotFoundException.class})
    public ResponseEntity<GlobalExceptionResponse> entityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), "404"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = {PasswordDontMatchException.class, IncorrectPasswordException.class})
    public ResponseEntity<GlobalExceptionResponse> passwordExceptionsHandler(RuntimeException e) {
        return new ResponseEntity<>(new GlobalExceptionResponse(e.getMessage(), "404"), HttpStatus.BAD_REQUEST);
    }
}
