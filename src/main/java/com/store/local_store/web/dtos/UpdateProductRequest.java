package com.store.local_store.web.dtos;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        BigDecimal price,
        Integer quantity
) {
}
