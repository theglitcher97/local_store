package com.store.local_store.application.use_cases;

import com.store.local_store.application.model.CreateProductCommand;
import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Category;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.services.CategoryService;
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
    private CategoryService categoryService;
    private ProductMapper productMapper;

    @Transactional
    public ProductDTO createProduct(CreateProductCommand createProduct) {
        // create product with category
        Category category = this.categoryService.findCategory(createProduct.categoryId());
        Product product = Product.create(createProduct.name(), createProduct.price(), createProduct.quantity(), category);
        this.productService.create(product); // set id on 'product' as side effect
        return this.productMapper.productToDto(product);
    }

    public PageResult<ProductDTO> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        PageResult<Product> productPage = this.productService.listProducts(page, size, sortBy, sortDir);
        List<ProductDTO> productDTOS = productPage.items().stream().map(p -> this.productMapper.productToDto(p)).toList();
        return new PageResult<>(productDTOS, productPage.page(), productPage.size(), productPage.totalItems(), productPage.totalPages());
    }
}
