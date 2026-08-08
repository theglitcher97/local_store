package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Category category;

    public static Product create(String name, Double price, Integer quantity, Category category) {
        // domain rules
        // should accept empty name, zero price, zero quantity or no category ?
        return new Product(null, name, price, quantity, category);
    }
}
