package com.store.local_store.application.model;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal price,
        Integer quantity,
        Long categoryId
) {}
