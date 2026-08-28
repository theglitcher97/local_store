package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.model.Order;

import java.util.List;

public interface OrderRepository {
    void create(Order order);

    List<Order> findAll(long userId);
}
