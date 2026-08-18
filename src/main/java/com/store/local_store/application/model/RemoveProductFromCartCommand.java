package com.store.local_store.application.model;

public record RemoveProductFromCartCommand(
        Long userId,
        Long productId,
        Boolean removeAll
) {
}
