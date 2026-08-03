package com.store.local_store.domain.services;

import com.store.local_store.domain.ports.repos.AccountRepository;
import com.store.local_store.web.exceptions.custom.EmailAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public void createAccount(String email, String password) {
        if (!this.accountRepository.isEmailAvailable(email))
            throw new EmailAlreadyExistsException("This email is already in use");
        this.accountRepository.createAccount(email, password);
    }

    public void authenticate(String email, String password) {
        this.accountRepository.authenticate(email, password);
    }
}
