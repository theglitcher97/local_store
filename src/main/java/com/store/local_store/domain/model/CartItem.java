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
}
