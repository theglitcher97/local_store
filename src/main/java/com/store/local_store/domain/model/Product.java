package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private Category category;

    public static Product create(String name, BigDecimal price, Integer stockQuantity, Category category) {
        // domain rules
        // should accept empty name, zero price, zero stockQuantity or no category ?
        return new Product(null, name, price, stockQuantity, category);
    }

    public boolean hasEnoughStock(Integer quantity) {
        return stockQuantity >= quantity;
    }

    public void decreaseStock(Integer quantity) {
        stockQuantity -= quantity;
        if (stockQuantity < 0)
            stockQuantity = 0;
    }
}
