package com.store.local_store.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Table(name = "cartItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, nullable = false)
    private CartEntity cart;

    @OneToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(updatable = false, nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Long quantity;
}
