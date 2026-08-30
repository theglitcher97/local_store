package com.store.local_store.web.dtos;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer availableStock,
        String categoryName
) {
}
