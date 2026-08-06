package com.store.local_store.domain.services;

import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.enums.SORT_DIR;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Long create(String name, Double price, Long categoryId) {
        return this.productRepository.create(name, price, categoryId);
    }

    public Page<ProductDTO> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        return this.productRepository.list(page, size, sortBy, sortDir);
    }
}
