package com.store.local_store.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer availableStock;
    @Column(nullable = false)
    private Integer reservedStock = 0;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CategoryEntity category;

    public static ProductEntity create(String name, BigDecimal price, int availableStock, CategoryEntity categoryEntity) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setAvailableStock(availableStock);
        productEntity.setCategory(categoryEntity);
        productEntity.setName(name);
        productEntity.setPrice(price);
        return productEntity;
    }
}
