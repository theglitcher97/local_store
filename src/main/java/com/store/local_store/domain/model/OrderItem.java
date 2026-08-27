package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal pricePerUnit;
    private Long quantity;

    public static OrderItem create(CartItem item) {
        OrderItem orderItem = new OrderItem();
        Product product = item.getProduct();

        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setPricePerUnit(product.getPrice());
        orderItem.setQuantity(item.getQuantity());
        return orderItem;
    }
}
