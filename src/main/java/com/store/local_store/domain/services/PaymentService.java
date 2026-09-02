package com.store.local_store.domain.services;

import com.store.local_store.domain.enums.PaymentMethods;
import com.store.local_store.domain.enums.PaymentStatus;
import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.Payment;
import com.store.local_store.domain.ports.repos.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private PaymentRepository paymentRepository;

    public Payment pay(Order order, PaymentMethods method) {
        Payment payment = Payment.create(order, method);
        payment.setStatus(this.simulatePaymentStatus());
        return this.paymentRepository.save(payment);
    }

    private PaymentStatus simulatePaymentStatus() {
        // randomness decides is the payment success or not
        return Math.random() > 0.5 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
    }
}
