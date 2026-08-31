package com.store.local_store.web.dtos;

import com.store.local_store.domain.enums.OrderState;

import java.math.BigDecimal;

public record BasicOrderDTO(
        Long id,
        Integer items,
        BigDecimal totalPrice,
        OrderState state
) {
}
