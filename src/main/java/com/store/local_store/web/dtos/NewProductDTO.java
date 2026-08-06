package com.store.local_store.web.dtos;

public record NewProductDTO(
        Long id,
        String name,
        Double price,
        Long categoryId
) {
}
