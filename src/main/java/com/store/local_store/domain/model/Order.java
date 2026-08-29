package com.store.local_store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private List<OrderItem> items;
    private String state;


    public static Order create(Long userId, BigDecimal totalPrice, List<OrderItem> items) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(totalPrice);
        order.setItems(items);
        order.setState("PENDING");
        return order;
    }

    public void cancel() {
        if (!state.equalsIgnoreCase("pending"))
            throw new RuntimeException("Cannot cancel an order if is not pending");
        state = "CANCELLED";
    }
}
