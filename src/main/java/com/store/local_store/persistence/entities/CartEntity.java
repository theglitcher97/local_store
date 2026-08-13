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
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private Set<CartItemEntity> items = new HashSet<>();

    public static CartEntity create(UserEntity userEntity) {
        return new CartEntity(null, userEntity, new HashSet<>());
    }

    public void addItem(CartItemEntity cartItem){
        this.items.add(cartItem);
        cartItem.setCart(this);
    }
}
