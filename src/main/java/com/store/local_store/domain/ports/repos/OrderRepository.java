package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.model.Order;

public interface OrderRepository {
    void create(Order order);
}
