package com.store.local_store.persistence.repositories;

import com.store.local_store.persistence.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {
}
