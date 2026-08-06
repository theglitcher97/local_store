package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.persistence.entities.CategoryEntity;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.persistence.repositories.CategoryEntityRepository;
import com.store.local_store.persistence.repositories.ProductEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class IProductRepository implements ProductRepository {
    private ProductEntityRepository productRepository;
    private CategoryEntityRepository categoryRepository;

    @Override
    public Long create(String name, Double price, Long categoryId) {
        Optional<CategoryEntity> optionalCategory = this.categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty())
            throw new EntityNotFoundException("Cannot find category with id: "+categoryId);

        ProductEntity productEntity = new ProductEntity(null, name, price, optionalCategory.get());
        productEntity =  this.productRepository.save(productEntity);
        return productEntity.getId();
    }
}
