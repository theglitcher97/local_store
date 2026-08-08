package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Product;
import com.store.local_store.web.enums.SORT_DIR;

public interface ProductRepository {

    Long create(Product product);

    PageResult<Product> list(Integer page, Integer size, String sortBy, SORT_DIR sortDir);
}
