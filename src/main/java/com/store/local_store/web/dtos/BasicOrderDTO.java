package com.store.local_store.web.dtos;

import java.math.BigDecimal;
import java.util.List;

public record BasicOrderDTO(
        Long id,
        Integer items,
        BigDecimal totalPrice
) {
}
