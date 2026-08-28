package com.store.local_store.application.use_cases;

import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.services.OrderService;
import com.store.local_store.web.dtos.BasicOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Component
public class OrderUseCases {
    private OrderService orderService;

    public List<BasicOrderDTO> findAllOrder(long userId) {
        List<Order> orders = this.orderService.findAll(userId);
        return orders.stream()
                .map(order -> new BasicOrderDTO(order.getId(), order.getItems().size(), order.getTotal()))
                .sorted(Comparator.comparing(BasicOrderDTO::id))
                .toList()
                .reversed();
    }
}
