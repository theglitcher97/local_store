package com.store.local_store.application.use_cases;

import com.store.local_store.domain.services.AccountService;
import com.store.local_store.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
