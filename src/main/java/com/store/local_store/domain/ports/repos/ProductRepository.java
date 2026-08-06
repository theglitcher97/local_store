package com.store.local_store.domain.ports.repos;

import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.enums.SORT_DIR;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductRepository {

    Long create(String name, Double price, Long categoryId);

    Page<ProductDTO> list(Integer page, Integer size, String sortBy, SORT_DIR sortDir);
}
