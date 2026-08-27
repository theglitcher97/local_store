package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long id;
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addProduct(Product product) {
        Optional<CartItem> optCartItem = findCartItemByProduct(product);
        if (optCartItem.isEmpty()) {
            CartItem cartItem = new CartItem(null, product, 1L);
            items.add(cartItem);
        }
        else {
           optCartItem.get().increaseQuantity();
        }
    }

    private Optional<CartItem> findCartItemByProduct(Product product) {
        return items.stream()
                .filter(i -> Objects.equals(i.getProduct().getId(), product.getId()))
                .findFirst();
    }

    public void clear() {
        this.items.clear();
    }

    public void reduceProductQuantity(Product product) {
        Optional<CartItem> cartItem = this.findCartItemByProduct(product);

        if (cartItem.isEmpty())
            return;

        cartItem.get().reduceProductQuantity();
        if (cartItem.get().isEmpty())
            this.items.remove(cartItem.get());
    }

    public void removeFromCart(Product product) {
        Optional<CartItem> cartItem = this.findCartItemByProduct(product);

        if (cartItem.isEmpty())
            return;

        this.items.remove(cartItem.get());
    }

    public BigDecimal getTotalPrice() {
        return this.getItems().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
