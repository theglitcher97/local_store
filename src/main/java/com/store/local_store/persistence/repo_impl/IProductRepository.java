package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.persistence.mapper.PageResultMapper;
import com.store.local_store.persistence.mapper.ProductMapper;
import com.store.local_store.persistence.repositories.ProductEntityRepository;
import com.store.local_store.web.enums.SORT_DIR;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class IProductRepository implements ProductRepository {
    private ProductEntityRepository productRepository;
    private ProductMapper productMapper;
    private PageResultMapper pageResultMapper;
    private EntityManager entityManager;

    @Override
    public Long create(Product product) {
        ProductEntity productEntity = this.productMapper.toEntity(product);
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

    @Override
    public Product findProduct(Long productId) {
        Optional<ProductEntity> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty())
            throw new EntityNotFoundException("Cannot find product with productId: "+productId);

        return this.productMapper.entityToModel(optionalProduct.get());
    }

    @Override
    public void saveAll(List<Product> productsToUpdate) {
        this.productRepository.saveAll(this.productMapper.toEntities(productsToUpdate));
    }

    public Integer updateProductStock(Long quantity, Long productId) {
        Query query = this.entityManager.createNativeQuery("UPDATE products \n" +
                "SET stock_quantity = stock_quantity - :quantity \n" +
                "WHERE id = :productId \n" +
                "AND stock_quantity >= :quantity");

        query.setParameter("quantity", quantity)
                .setParameter("productId", productId);

        return query.executeUpdate();
    }

    @Override
    public void save(Product product) {
        this.productRepository.save(this.productMapper.toEntity(product));
    }
}
