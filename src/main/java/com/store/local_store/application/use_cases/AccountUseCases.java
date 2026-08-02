package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountUseCases {
    private AccountService accountService;

    public void createAccount(String email, String password) {
        this.accountService.createAccount(email, password);
    }
}
