package com.store.local_store.domain.model;

import com.store.local_store.domain.enums.PaymentMethods;
import com.store.local_store.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Setter
    private Long id;
    @Setter
    private Long orderId;
    @Setter
    private BigDecimal amount;
    @Setter
    private PaymentMethods method;
    private PaymentStatus status;

    public void setStatus(PaymentStatus status) {
        if (!Objects.isNull(this.status))
            throw new RuntimeException("A Payment's status can only be assigned once");
        this.status = status;
    }

    public static Payment create(Order order, PaymentMethods method) {
        Payment payment = new Payment();
        payment.orderId = order.getId();
        payment.amount = order.getTotal();
        payment.method = method;
        return payment;
    }
}
