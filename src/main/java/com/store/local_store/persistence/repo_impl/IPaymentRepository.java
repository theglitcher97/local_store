package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.Payment;
import com.store.local_store.domain.ports.repos.PaymentRepository;
import com.store.local_store.persistence.entities.OrderEntity;
import com.store.local_store.persistence.entities.PaymentEntity;
import com.store.local_store.persistence.mapper.PaymentMapper;
import com.store.local_store.persistence.repositories.OrderEntityRepository;
import com.store.local_store.persistence.repositories.PaymentEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class IPaymentRepository implements PaymentRepository {
    private PaymentEntityRepository paymentRepository;
    private OrderEntityRepository orderRepository;
    private PaymentMapper paymentMapper;

    @Override
    public Payment save(Payment payment) {
        Optional<OrderEntity> optionalOrder = this.orderRepository.findById(payment.getOrderId());
        if (optionalOrder.isEmpty())
            throw new EntityNotFoundException("Cannot find order when trying to pay; order id: "+payment.getOrderId());

        PaymentEntity paymentEntity = this.paymentMapper.toEntity(payment);
        paymentEntity.setOrder(optionalOrder.get());
        paymentEntity = this.paymentRepository.save(paymentEntity);
        payment.setId(paymentEntity.getId());
        return payment;
    }
}
