package com.store.local_store.domain.services;

import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.ports.repos.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public Order findOrder(Long id, long userId) {
        return this.orderRepository.findByIdAndUserId(id, userId);
    }

    public List<Order> findAll(long userId) {
        return this.orderRepository.findAll(userId);
    }

    public List<Order> findAll(long userId, String state) {
        return this.orderRepository.findAllWithState(userId, state);

    }
}
