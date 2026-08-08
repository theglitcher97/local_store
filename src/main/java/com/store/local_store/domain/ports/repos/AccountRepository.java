package com.store.local_store.domain.ports.repos;

import com.store.local_store.domain.model.User;

public interface AccountRepository {
    void createAccount(String email, String password);

    User authenticate(String email, String password);

    boolean isEmailAvailable(String email);
}
