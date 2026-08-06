package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.ProductService;
import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.enums.SORT_DIR;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@AllArgsConstructor
public class ProductUseCases {
    private ProductService productService;

    @Transactional
    public Long createProduct(String name, Double price, Long categoryId) {
        // create product with category
        return this.productService.create(name, price, categoryId);
    }

    public Page<ProductDTO> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        return this.productService.listProducts(page, size, sortBy, sortDir);
    }
}
