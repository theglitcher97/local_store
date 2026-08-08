package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.model.Category;

public interface CategoryRepository {

    Category findCategory(Long categoryId);
}
