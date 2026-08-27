package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.OrderItem;
import com.store.local_store.domain.ports.repos.OrderRepository;
import com.store.local_store.persistence.entities.OrderEntity;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.mapper.OrderItemMapper;
import com.store.local_store.persistence.repositories.OrderEntityRepository;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class IOrderRepository implements OrderRepository {
    private OrderEntityRepository orderRepository;
    private OrderItemMapper orderItemMapper;
    private UserEntityRepository userRepository;

    @Override
    public void create(Order order) {
        UserEntity user = this.userRepository.findById(order.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user for order; user productId:" +order.getUserId()));

        OrderEntity orderEntity = this.toEntity(order, user);
        this.orderRepository.save(orderEntity);
    }

    private OrderEntity toEntity(Order order, UserEntity userEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(userEntity);
        orderEntity.setTotal(order.getTotal());

        for (OrderItem item : order.getItems()) {
            orderEntity.addItem(this.orderItemMapper.toEntity(item));
        }

        return orderEntity;
    }
}
