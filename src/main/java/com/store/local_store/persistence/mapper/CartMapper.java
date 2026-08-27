package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Cart;
import com.store.local_store.persistence.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class, UserMapper.class})
public interface CartMapper {
    Cart toModel(CartEntity cartEntity);

    @Mapping(target = "user", ignore = true)
    CartEntity toEntity(Cart cart);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    CartEntity toEntityNoItems(Cart cart);
}
