package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.AccountService;
import com.store.local_store.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class AccountUseCases {
    private AccountService accountService;
    private JwtUtils jwtUtils;

    public void createAccount(String email, String password) {
        this.accountService.createAccount(email, password);
    }

    @Transactional
    public String login(String email, String password) {
        this.accountService.authenticate(email, password);
        return this.jwtUtils.generateToken(email);
    }
}
