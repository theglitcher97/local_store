package com.store.local_store.domain.model;

import com.store.local_store.domain.enums.OrderState;
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
    private OrderState state;


    public static Order create(Long userId, BigDecimal totalPrice, List<OrderItem> items) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotal(totalPrice);
        order.setItems(items);
        order.setState(OrderState.PENDING);
        return order;
    }

    private void setState(){};

    public void cancel() {
        if (state != OrderState.PENDING)
            throw new RuntimeException("Cannot cancel an order if is not pending");
        state = OrderState.CANCELLED;
    }

    public void complete() {
        if (state != OrderState.PENDING)
            throw new RuntimeException("Cannot complete an order if is not pending");
        state = OrderState.COMPLETED;
    }

    public void validatePayment() {
        if (state != OrderState.PENDING)
            throw new RuntimeException("Cannot complete an order if is not pending");
    }
}
