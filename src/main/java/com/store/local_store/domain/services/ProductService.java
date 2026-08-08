package com.store.local_store.domain.services;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.web.enums.SORT_DIR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product create(Product product) {
        Long id = this.productRepository.create(product);
        product.setId(id);
        return product;
    }

    public PageResult<Product> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        return this.productRepository.list(page, size, sortBy, sortDir);
    }
}
