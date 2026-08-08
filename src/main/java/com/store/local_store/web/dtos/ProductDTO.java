package com.store.local_store.web.dtos;

public record ProductDTO(
        Long id,
        String name,
        Double price,
        Integer quantity,
        String categoryName
) {
}
