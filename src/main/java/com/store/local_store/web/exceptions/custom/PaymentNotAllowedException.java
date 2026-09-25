package com.store.local_store.web.exceptions.custom;

public class PaymentNotAllowedException extends RuntimeException {
    public PaymentNotAllowedException(String message) {
        super(message);
    }
}
