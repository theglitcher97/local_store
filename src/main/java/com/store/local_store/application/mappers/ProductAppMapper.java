package com.store.local_store.application.mappers;

import com.store.local_store.domain.model.Product;
import com.store.local_store.web.dtos.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductAppMapper {
    @Mapping(target = "categoryName", ignore = true)
    ProductDTO productToDto(Product product);
}
