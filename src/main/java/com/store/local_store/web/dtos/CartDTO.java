package com.store.local_store.web.dtos;

import java.math.BigDecimal;
import java.util.List;

public record  CartDTO(
        Long id,
        List<CartItemDTO> item,
        BigDecimal totalPrice
) {
}
