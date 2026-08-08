package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.User;
import com.store.local_store.domain.ports.repos.AccountRepository;
import com.store.local_store.persistence.entities.UserEntity;
import com.store.local_store.persistence.mapper.UserMapper;
import com.store.local_store.persistence.repositories.UserEntityRepository;
import com.store.local_store.web.exceptions.custom.IncorrectPasswordException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
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
    private UserMapper userMapper;

    @Override
    public void createAccount(String email, String password) {
        // create and save account
        UserEntity userEntity =  UserEntity.create(email, this.passwordEncoder.encode(password), "ROLE_CUSTOMER");
        this.userRepository.save(userEntity);

        // link cart
//        CartEntity cart = new CartEntity(null, userEntity, new HashSet<>());
//        userEntity.setCart(cart);
//        this.userRepository.save(userEntity);
    }

    @Override
    public User authenticate(String email, String password) {
        Optional<UserEntity> userEntity = this.userRepository.findByEmail(email);

        userEntity.ifPresentOrElse(user -> {
            if (!this.passwordEncoder.matches(password, user.getPassword()))
                throw new IncorrectPasswordException("Password is incorrect");
        }, () -> {
            throw new EntityNotFoundException("Cannot find account for email: "+email);
        });

        return this.userMapper.entityToModel(userEntity.get());
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !this.userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String userId) throws UsernameNotFoundException {
        return this.userRepository.findById(Long.parseLong(userId)).get();
    }
}
