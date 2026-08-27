package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.ports.repos.OrderRepository;
import com.store.local_store.persistence.entities.OrderEntity;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.mapper.OrderMapper;
import com.store.local_store.persistence.repositories.OrderEntityRepository;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class IOrderRepository implements OrderRepository {
    private OrderEntityRepository orderRepository;
    private OrderMapper orderMapper;
    private UserEntityRepository userRepository;

    @Override
    public void create(Order order) {
        UserEntity user = this.userRepository.findById(order.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot found user for order; user id:" +order.getUserId()));

        OrderEntity orderEntity = this.orderMapper.toEntity(order);
        orderEntity.setUser(user);
        orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        this.orderRepository.save(orderEntity);
    }
}
