package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.persistence.mapper.PageResultMapper;
import com.store.local_store.persistence.mapper.ProductMapper;
import com.store.local_store.persistence.repositories.ProductEntityRepository;
import com.store.local_store.web.enums.SORT_DIR;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IProductRepository implements ProductRepository {
    private ProductEntityRepository productRepository;
    private ProductMapper productMapper;
    private PageResultMapper pageResultMapper;

    @Override
    public Long create(Product product) {
        ProductEntity productEntity = this.productMapper.modelToEntity(product);
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
