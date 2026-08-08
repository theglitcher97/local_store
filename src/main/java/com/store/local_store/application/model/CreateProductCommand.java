package com.store.local_store.application.model;

public record CreateProductCommand(
        String name,
        Double price,
        Integer quantity,
        Long categoryId
) {}
