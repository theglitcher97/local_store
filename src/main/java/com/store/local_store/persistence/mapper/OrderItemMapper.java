package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.OrderItem;
import com.store.local_store.persistence.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "order", ignore = true)
    OrderItemEntity toEntity(OrderItem orderItem);
    List<OrderItemEntity> toEntities(List<OrderItem> orderItems);
}
