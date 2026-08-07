package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Product;
import com.store.local_store.persistence.entities.ProductEntity;
import com.store.local_store.web.dtos.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    Product entityToModel(ProductEntity productEntity);
    ProductEntity modelToEntity(Product product);

    @Mapping(target = "categoryName", source = "category.name")
    ProductDTO productToDto(Product product);
}
