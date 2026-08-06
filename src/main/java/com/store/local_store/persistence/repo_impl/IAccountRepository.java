package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.ports.repos.AccountRepository;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import com.store.local_store.web.exceptions.custom.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class IAccountRepository implements AccountRepository, UserDetailsService {
    private UserEntityRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void createAccount(String email, String password) {
        UserEntity userEntity = new UserEntity(null, email, this.passwordEncoder.encode(password), "ROLE_CUSTOMER");
        this.userRepository.save(userEntity);
    }

    @Override
    public void authenticate(String email, String password) {
        Optional<UserEntity> userEntity = this.userRepository.findByEmail(email);
        userEntity.ifPresentOrElse(user -> {
            if (!this.passwordEncoder.matches(password, user.getPassword()))
                throw new IncorrectPasswordException("Password is incorrect");
        }, () -> {
            throw new EntityNotFoundException("Cannot find account for email: "+email);
        });
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !this.userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).get();
    }
}
