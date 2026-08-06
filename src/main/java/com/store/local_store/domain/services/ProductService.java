package com.store.local_store.domain.services;

import com.store.local_store.domain.ports.repos.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Long create(String name, Double price, Long categoryId) {
        return this.productRepository.create(name, price, categoryId);
    }
}
