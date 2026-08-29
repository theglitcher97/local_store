package com.store.local_store.application.model;

public record CancelOrderCommand(
        Long orderId,
        Long userId
) {
}
