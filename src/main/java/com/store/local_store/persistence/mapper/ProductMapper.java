package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Product;
import com.store.local_store.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    Product entityToModel(ProductEntity productEntity);
    ProductEntity modelToEntity(Product product);


}
