package com.store.local_store.application.model;

public record AddProductToCartCommand(
        Long userId,
        Long productId
) {
}
