package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.ports.repos.AccountRepository;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IAccountRepository implements AccountRepository {
    private UserEntityRepository userRepository;

    @Override
    public void createAccount(String email, String password) {
        UserEntity userEntity = new UserEntity(null, email, password, "ROLE_CUSTOMER");
        this.userRepository.save(userEntity);
    }
}
