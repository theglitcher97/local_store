package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Product;
import com.store.local_store.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    Product entityToModel(ProductEntity productEntity);

    ProductEntity toEntity(Product product);
    List<ProductEntity> toEntities(List<Product> product);
}
