package com.store.local_store.domain.common;

import java.util.List;

public record PageResult<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        long totalPages
) {}
