package com.store.local_store.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "orderItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, nullable = false)
    private OrderEntity order;

    @Column(updatable = false, nullable = false)
    private Long productId;

    @Column(updatable = false, nullable = false)
    private String productName;

    @Column(updatable = false, nullable = false)
    private BigDecimal pricePerUnit;

    @Column(updatable = false, nullable = false)
    private Integer quantity;
}
