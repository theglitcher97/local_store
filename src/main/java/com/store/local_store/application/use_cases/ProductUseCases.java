package com.store.local_store.application.use_cases;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.services.ProductService;
import com.store.local_store.persistence.mapper.ProductMapper;
import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.enums.SORT_DIR;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@AllArgsConstructor
public class ProductUseCases {
    private ProductService productService;
    private ProductMapper productMapper;

    @Transactional
    public Long createProduct(String name, Double price, Long categoryId) {
        // create product with category
        return this.productService.create(name, price, categoryId);
    }

    public PageResult<ProductDTO> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        PageResult<Product> productPage = this.productService.listProducts(page, size, sortBy, sortDir);
        List<ProductDTO> productDTOS = productPage.items().stream().map(p -> this.productMapper.productToDto(p)).toList();
        return new PageResult<>(productDTOS, productPage.page(), productPage.size(), productPage.totalItems(), productPage.totalPages());
    }
}
