package com.store.local_store.web.dtos;

import java.math.BigDecimal;
import java.util.List;

public record FullOrderDTO(
        Long id,
        List<OrderItemDTO> items,
        BigDecimal totalPrice
) {
}
