package com.store.local_store.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table(name = "carts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItemEntity> cartItems = new HashSet<>();
}
