package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long id;
    private Product product;
    private Long quantity;

    public void increaseQuantity() {
        quantity++;
    }

    public void reduceProductQuantity() {
        if (quantity <= 0)
            throw new RuntimeException("CartItem quantity cannot be zero or less before calling this method");

        quantity--;
    }

    public boolean isEmpty() {
        return quantity == 0;
    }

    public BigDecimal getSubtotal() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }
}
