package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Order;
import com.store.local_store.persistence.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "user", ignore = true)
    OrderEntity toEntity(Order order);

    @Mapping(target = "userId", source = "user.id")
    Order toModel(OrderEntity orderEntity);
    List<Order> toModel(List<OrderEntity> orderEntities);
}
