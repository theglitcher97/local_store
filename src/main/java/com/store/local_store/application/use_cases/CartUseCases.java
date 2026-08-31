package com.store.local_store.application.use_cases;

import com.store.local_store.application.mappers.CartItemAppMapper;
import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.application.model.RemoveProductFromCartCommand;
import com.store.local_store.domain.model.*;
import com.store.local_store.domain.ports.repos.CartRepository;
import com.store.local_store.domain.ports.repos.OrderRepository;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.domain.services.ProductService;
import com.store.local_store.web.dtos.CartDTO;
import com.store.local_store.web.dtos.CartItemDTO;
import com.store.local_store.web.exceptions.custom.EmptyCartException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
public class CartUseCases {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartItemAppMapper cartItemMapper;
    private OrderRepository orderRepository;
    private ProductService productService;

    @Transactional
    public void addProduct(AddProductToCartCommand productToCard) {
        Cart cart = this.cartRepository.findCartForUser(productToCard.userId());
        Product product = this.productRepository.findProduct(productToCard.productId());
        cart.addProduct(product);
        this.cartRepository.save(cart);
    }

    public CartDTO getCart(long userId) {
        Cart cart = this.cartRepository.findCartForUser(userId);
        List<CartItem> items = cart.getItems().stream().sorted(Comparator.comparing(CartItem::getId)).toList();
        cart.setItems(items);
        return this.toCartDTO(cart);
    }

    private CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> items = this.cartItemMapper.toDtoList(cart.getItems());
        BigDecimal totalPrice = cart.getTotalPrice();
        return new CartDTO(cart.getId(), items, totalPrice);
    }

    @Transactional()
    public void checkout(long userId) {
        Cart cart = this.cartRepository.findCartForUser(userId);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        cart.getItems()
                .stream().sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .forEach(item -> {
                    this.productService.reserveProduct(item.getProduct(), item.getQuantity());
                });

        List<OrderItem> orderItems = cart.getItems().stream().map(OrderItem::create).toList();
        Order order = Order.create(userId, cart.getTotalPrice(), orderItems);
        this.orderRepository.create(order);

        cart.clear();
        this.cartRepository.save(cart);
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