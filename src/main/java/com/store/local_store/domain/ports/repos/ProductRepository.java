package com.store.local_store.domain.ports.repos;

public interface ProductRepository {

    Long create(String name, Double price, Long categoryId);
}
