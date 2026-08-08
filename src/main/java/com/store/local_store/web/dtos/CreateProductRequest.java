package com.store.local_store.web.dtos;

public record CreateProductRequest(
        String name,
        Double price,
        Integer quantity,
        Long categoryId
) {
}
