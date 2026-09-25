package com.store.local_store.web.exceptions.custom;

public class InvalidCartStateException extends RuntimeException {
    public InvalidCartStateException(String message) {
        super(message);
    }
}
