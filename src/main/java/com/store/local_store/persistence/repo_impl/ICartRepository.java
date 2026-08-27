package com.store.local_store.persistence.repo_impl;

import com.store.local_store.domain.model.Cart;
import com.store.local_store.domain.model.CartItem;
import com.store.local_store.domain.ports.repos.CartRepository;
import com.store.local_store.persistence.entities.CartEntity;
import com.store.local_store.persistence.entities.CartItemEntity;
import com.store.local_store.persistence.mapper.CartItemMapper;
import com.store.local_store.persistence.mapper.CartMapper;
import com.store.local_store.persistence.repositories.CartEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ICartRepository implements CartRepository {
    private CartEntityRepository cartRepository;
    private CartMapper cartMapper;
    private CartItemMapper cartItemMapper;

    @Override
    public Cart findCartForUser(Long userId) {
        Optional<CartEntity> optionalCart = this.cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty())
            throw new EntityNotFoundException("Cannot find cart for user with productId: "+userId);

        return this.cartMapper.toModel(optionalCart.get());
    }

    @Override
    public void save(Cart cart) {
        CartEntity cartEntity = this.toEntity(cart);
        this.cartRepository.save(cartEntity);
    }

    private CartEntity toEntity(Cart cart) {
        CartEntity cartEntity = this.cartMapper.toEntityNoItems(cart);
        for (CartItem cartItem : cart.getItems()) {
            CartItemEntity cartItemEntity = this.cartItemMapper.toEntity(cartItem);
            cartEntity.addItem(cartItemEntity);
        }
        return cartEntity;
    }
}
