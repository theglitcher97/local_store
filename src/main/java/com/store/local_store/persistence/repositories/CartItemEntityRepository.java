package com.store.local_store.persistence.repositories;

import com.store.local_store.persistence.entities.CartItemEntity;
import com.store.local_store.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, Long> {
}
