package com.store.local_store.domain.services;

import com.store.local_store.domain.model.Category;
import com.store.local_store.domain.ports.repos.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public Category findCategory(Long categoryId) {
        return this.categoryRepository.findCategory(categoryId);
    }
}
