package com.store.local_store.persistence.entities;

import com.store.local_store.domain.enums.PaymentMethods;
import com.store.local_store.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", updatable = false, nullable = false)
    private OrderEntity order;

    @Column(updatable = false)
    private BigDecimal amount;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethods method;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
