package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.persistence.entities.CategoryEntity;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.persistence.mapper.PageResultMapper;
import com.store.local_store.persistence.mapper.ProductMapper;
import com.store.local_store.persistence.repositories.CategoryEntityRepository;
import com.store.local_store.persistence.repositories.ProductEntityRepository;
import com.store.local_store.web.enums.SORT_DIR;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class IProductRepository implements ProductRepository {
    private ProductEntityRepository productRepository;
    private CategoryEntityRepository categoryRepository;
    private ProductMapper productMapper;
    private PageResultMapper pageResultMapper;

    @Override
    public Long create(String name, Double price, Long categoryId) {
        Optional<CategoryEntity> optionalCategory = this.categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty())
            throw new EntityNotFoundException("Cannot find category with id: "+categoryId);

        ProductEntity productEntity = new ProductEntity(null, name, price, optionalCategory.get());
        productEntity =  this.productRepository.save(productEntity);
        return productEntity.getId();
    }

    @Override
    public PageResult<Product> list(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        Sort sort = Sort.by(sortBy);

        if (sortDir == SORT_DIR.ASC) sort = sort.ascending();
        else sort = sort.descending();

        Page<Product> productPage = this.productRepository
                .findAll(PageRequest.of(page, size, sort))
                .map(entity -> this.productMapper.entityToModel(entity));

        return this.pageResultMapper.toPageResult(productPage);
    }
}
