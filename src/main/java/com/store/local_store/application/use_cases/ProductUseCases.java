package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@AllArgsConstructor
public class ProductUseCases {
    private ProductService productService;

    @Transactional
    public Long createProduct(String name, Double price, Long categoryId) {
        // create product with category
        return this.productService.create(name, price, categoryId);
    }
}
