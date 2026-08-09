package com.store.local_store.web.dtos;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        BigDecimal price,
        Integer quantity,
        Long categoryId
) {
}
