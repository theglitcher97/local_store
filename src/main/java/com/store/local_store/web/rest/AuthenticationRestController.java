package com.store.local_store.web.rest;

import com.store.local_store.application.use_cases.AccountUseCases;
import com.store.local_store.web.dtos.CreateAccountDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@AllArgsConstructor
public class AuthenticationRestController {
    private AccountUseCases accountUseCases;

    @PostMapping("/signup")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        // TODO: validate email is valid

        if (!createAccountDTO.password().equals(createAccountDTO.confirmPassword()))
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        this.accountUseCases.createAccount(createAccountDTO.email(), createAccountDTO.password());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
