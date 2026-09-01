package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.enums.OrderState;
import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.OrderItem;
import com.store.local_store.domain.ports.repos.OrderRepository;
import com.store.local_store.persistence.entities.OrderEntity;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.mapper.OrderItemMapper;
import com.store.local_store.persistence.mapper.OrderMapper;
import com.store.local_store.persistence.repositories.OrderEntityRepository;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class IOrderRepository implements OrderRepository {
    private OrderEntityRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private UserEntityRepository userRepository;
    private IProductRepository productRepository;

    @Override
    public void create(Order order) {
        UserEntity user = this.userRepository.findById(order.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user for order; user productId:" +order.getUserId()));

        OrderEntity orderEntity = this.toEntity(order, user);
        this.orderRepository.save(orderEntity);
    }

    @Override
    public List<Order> findAll(long userId) {
        List<OrderEntity> orderEntities = this.orderRepository.findAllByUser(userId);
        return this.orderMapper.toModel(orderEntities);
    }

    @Override
    public Order findByIdAndUserId(Long id, long userId) {
        Optional<OrderEntity> optionalOrder = this.orderRepository.findByIdAndUserId(id, userId);
        return optionalOrder.map(orderEntity -> this.orderMapper.toModel(orderEntity)).orElse(null);
    }

    @Override
    public List<Order> findAllWithState(long userId, OrderState state) {
        List<OrderEntity> orderEntities = this.orderRepository.findAllByUserAndState(userId, state);
        return this.orderMapper.toModel(orderEntities);
    }

    @Override
    public void save(Order order) {
        OrderEntity orderEntity = this.toEntity(order);
        this.orderRepository.save(orderEntity);
    }

    private OrderEntity toEntity(Order order, UserEntity userEntity) {
        OrderEntity orderEntity = this.toEntity(order);
        orderEntity.setUser(userEntity);
        return orderEntity;
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setTotal(order.getTotal());
        orderEntity.setState(order.getState());

        for (OrderItem item : order.getItems()) {
            orderEntity.addItem(this.orderItemMapper.toEntity(item));
        }

        return orderEntity;
    }
}
