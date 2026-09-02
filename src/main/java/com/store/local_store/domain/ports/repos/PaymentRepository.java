package com.store.local_store.domain.ports.repos;


import com.store.local_store.domain.model.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);
}
