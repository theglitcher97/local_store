package com.store.local_store.application.use_cases;

import com.store.local_store.application.mappers.CartItemAppMapper;
import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.application.model.RemoveProductFromCartCommand;
import com.store.local_store.domain.model.Cart;
import com.store.local_store.domain.model.CartItem;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.CartRepository;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.web.dtos.CartDTO;
import com.store.local_store.web.dtos.CartItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartUseCases {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartItemAppMapper cartItemMapper;

    @Transactional
    public void addProduct(AddProductToCartCommand productToCard) {
        Cart cart = this.cartRepository.findCartForUser(productToCard.userId());
        Product product = this.productRepository.findProduct(productToCard.productId());
        cart.addProduct(product);
        this.cartRepository.save(cart);
    }

    public CartDTO getCart(long userId) {
        Cart cart = this.cartRepository.findCartForUser(userId);
        return this.toCartDTO(cart);
    }

    private CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> items = this.cartItemMapper.toDtoList(cart.getItems());
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return new CartDTO(cart.getId(), items, totalPrice);
    }

    @Transactional()
    public void checkout(long userId) {
        Cart cart = this.cartRepository.findCartForUser(userId);
        boolean hasInsufficientStock = cart.getItems().stream()
                .anyMatch(item -> !item.getProduct().hasEnoughStock(item.getQuantity()));

        if (hasInsufficientStock)
            // later throw custom exception
            // what should happen to a product (in cart) if it no longer has stock ?
            throw new RuntimeException("At least one of the products have not enough stock!");


        // decrease product stock
        cart.getItems().forEach(item -> {
            item.getProduct().decreaseStock(item.getQuantity());
        });
        List<Product> productsToUpdate = cart.getItems().stream().map(CartItem::getProduct).collect(Collectors.toList());

        // should I do this after saving the cart, so the cart will automatically
        // update the products and save again ?
        cart.clear();

        this.cartRepository.save(cart);
        this.productRepository.saveAll(productsToUpdate);
    }

    @Transactional
    public void removeProduct(RemoveProductFromCartCommand command) {
        Cart cart = this.cartRepository.findCartForUser(command.userId());
        Product product = this.productRepository.findProduct(command.productId());

        if (command.removeAll()) cart.removeFromCart(product);
        else cart.reduceProductQuantity(product);
        this.cartRepository.save(cart);
    }
}