package com.store.local_store.domain.services;

import com.store.local_store.domain.ports.repos.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public void createAccount(String email, String password) {
        this.accountRepository.createAccount(email, password);
    }
}
