package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.Category;
import com.store.local_store.domain.ports.repos.CategoryRepository;
import com.store.local_store.persistence.mapper.CategoryMapper;
import com.store.local_store.persistence.repositories.CategoryEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ICategoryRepository implements CategoryRepository {
    private CategoryEntityRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public Category findCategory(Long categoryId) {
        return this.categoryRepository.findById(categoryId)
                .map(entity -> this.categoryMapper.entityToModel(entity))
                .orElseThrow(() -> new EntityNotFoundException("Cannot find category with id: "+categoryId));
    }
}
