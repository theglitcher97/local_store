package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Category;
import com.store.local_store.persistence.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category entityToModel(CategoryEntity categoryEntity);

    CategoryEntity modelToEntity(Category category);
}
