package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Product product;
    private Integer quantity;

    public void increaseQuantity() {
        quantity++;
    }

    public void reduceProductAmount() {
        if (quantity <= 0)
            throw new RuntimeException("CartItem quantity cannot be zero or less before calling this method");

        quantity--;
    }

    public boolean isEmpty() {
        return quantity == 0;
    }
}
