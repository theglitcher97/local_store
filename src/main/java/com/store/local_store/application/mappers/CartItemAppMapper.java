package com.store.local_store.application.mappers;

import com.store.local_store.domain.model.CartItem;
import com.store.local_store.persistence.mapper.ProductMapper;
import com.store.local_store.web.dtos.CartItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemAppMapper {
    CartItemDTO toDto(CartItem cartItem);
    List<CartItemDTO> toDtoList(List<CartItem> items);
}
