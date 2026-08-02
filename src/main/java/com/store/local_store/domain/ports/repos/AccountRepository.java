package com.store.local_store.domain.ports.repos;

public interface AccountRepository {
    void createAccount(String email, String password);
}
