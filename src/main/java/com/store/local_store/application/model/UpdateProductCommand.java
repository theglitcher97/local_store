package com.store.local_store.application.model;

import java.math.BigDecimal;

public record UpdateProductCommand(
        Long productId,
        String name,
        BigDecimal price,
        Integer quantity
) {}
