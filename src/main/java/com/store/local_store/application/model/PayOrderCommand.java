package com.store.local_store.application.model;

import com.store.local_store.domain.enums.PaymentMethods;

public record PayOrderCommand(
        Long orderId,
        PaymentMethods method,
        Long userId
) {
}
