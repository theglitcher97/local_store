package com.store.local_store.web.dtos;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long id,
        String productName,
        BigDecimal pricePerUnit,
        Long quantity
) {
}
