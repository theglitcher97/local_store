package com.store.local_store.application.use_cases;

import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.domain.model.Cart;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.CartRepository;
import com.store.local_store.domain.ports.repos.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class CartUseCases {
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    @Transactional
    public void addProduct(AddProductToCartCommand productToCard) {
        Cart cart = this.cartRepository.findCartForUser(productToCard.userId());
        Product product = this.productRepository.findProduct(productToCard.productId());
        cart.addProduct(product);
        this.cartRepository.save(cart);
    }
}