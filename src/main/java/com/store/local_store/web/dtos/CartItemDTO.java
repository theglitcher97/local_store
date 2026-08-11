package com.store.local_store.web.dtos;

public record CartItemDTO(
        Long id,
        Integer quantity,
        ProductDTO product
) {
}
