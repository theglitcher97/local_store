package com.store.local_store.web.exceptions.handlers;

import com.store.local_store.web.dtos.GlobalExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ZGlobalExceptionHandler {
    @ExceptionHandler(exception = {RuntimeException.class})
    public ResponseEntity<GlobalExceptionResponse> runtimeException(RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new GlobalExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
