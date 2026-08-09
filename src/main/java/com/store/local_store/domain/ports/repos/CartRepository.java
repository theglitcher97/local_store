package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.model.Cart;

public interface CartRepository {
    Cart findCartForUser(Long aLong);

    void save(Cart cart);
}
