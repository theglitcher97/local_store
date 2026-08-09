package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.CartItem;
import com.store.local_store.persistence.entities.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemMapper {
    CartItem toModel(CartItemEntity cartItemEntity);

    CartItemEntity toEntity(CartItem cartItem);
}
