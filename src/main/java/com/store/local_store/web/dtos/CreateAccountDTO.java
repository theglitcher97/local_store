package com.store.local_store.web.dtos;

public record CreateAccountDTO(
        String email,
        String password,
        String confirmPassword
) {
}
