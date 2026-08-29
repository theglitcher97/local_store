package com.store.local_store.web.dtos;

import java.math.BigDecimal;

public record BasicOrderDTO(
        Long id,
        Integer items,
        BigDecimal totalPrice,
        String state
) {
}
