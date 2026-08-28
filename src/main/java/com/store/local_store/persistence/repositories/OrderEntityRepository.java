package com.store.local_store.persistence.repositories;

import com.store.local_store.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT oe FROM OrderEntity oe WHERE oe.user.id = :userId")
    List<OrderEntity> findAllByUser(@Param("userId") long userId);
}
