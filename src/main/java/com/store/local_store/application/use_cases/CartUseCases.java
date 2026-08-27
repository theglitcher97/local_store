package com.store.local_store.application.use_cases;

import com.store.local_store.application.mappers.CartItemAppMapper;
import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.application.model.RemoveProductFromCartCommand;
import com.store.local_store.domain.model.Cart;
import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.OrderItem;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.CartRepository;
import com.store.local_store.domain.ports.repos.OrderRepository;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.web.dtos.CartDTO;
import com.store.local_store.web.dtos.CartItemDTO;
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
        BigDecimal totalPrice = cart.getTotalPrice();
        return new CartDTO(cart.getId(), items, totalPrice);
    }

    @Transactional()
    public void checkout(long userId) {
        Cart cart = this.cartRepository.findCartForUser(userId);

        // validate if cart have items before checking out

        cart.getItems()
                .stream().sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .forEach(item -> {
                    Integer rowsAffected = this.productRepository.updateProductStock(item.getQuantity(), item.getProduct().getId());
                    if (rowsAffected == 0)
                        // replace by InsufficientStockException(productId, productName, quantityRequired)
                        throw new RuntimeException("Not enough stock for product: "+item.getProduct().getName());
                });

        List<OrderItem> orderItems = cart.getItems().stream().map(OrderItem::create).toList();
        Order order = new Order(null, userId, cart.getTotalPrice(), orderItems);
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