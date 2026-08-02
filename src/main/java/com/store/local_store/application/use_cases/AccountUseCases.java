package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.AccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountUseCases {
    private AccountService accountService;

    public void createAccount(String email, String password) {
        // create account
        this.accountService.createAccount(email, password);
        // send confirmation email
    }
}
