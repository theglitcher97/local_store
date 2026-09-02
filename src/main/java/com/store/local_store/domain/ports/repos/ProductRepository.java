package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.web.enums.SORT_DIR;

import java.util.List;

public interface ProductRepository {

    Long create(Product product);

    PageResult<Product> list(Integer page, Integer size, String sortBy, SORT_DIR sortDir);

    Product findProduct(Long id);

    void saveAll(List<Product> productsToUpdate);

    Integer reserveProductStock(Long productId, Long quantity);

    void save(Product product);

    Integer freeReservedStock(Long productId, Long quantity);

    Integer removeReserve(Long productId, Long quantity);
}
