package com.store.local_store.application.use_cases;

import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.services.OrderService;
import com.store.local_store.web.dtos.BasicOrderDTO;
import com.store.local_store.web.dtos.FullOrderDTO;
import com.store.local_store.web.dtos.OrderItemDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Component
public class OrderUseCases {
    private OrderService orderService;

    public List<BasicOrderDTO> findAllOrder(long userId) {
        List<Order> orders = this.orderService.findAll(userId);
        return orders.stream()
                .map(order -> new BasicOrderDTO(order.getId(), order.getItems().size(), order.getTotal()))
                // lower to bigger -> (reversed) bigger to lower
                .sorted(Comparator.comparing(BasicOrderDTO::id, Comparator.reverseOrder()))
                .toList();
    }

    public FullOrderDTO findOrder(Long id, long userId) {
        Order order = this.orderService.findOrder(id, userId);
        if (Objects.isNull(order))
            throw new EntityNotFoundException("Cannot find order for user");

        List<OrderItemDTO> itemDTOS = order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getId(), item.getProductName(), item.getPricePerUnit(), item.getQuantity()))
                .sorted(Comparator.comparing(OrderItemDTO::pricePerUnit, Comparator.reverseOrder()))
                .toList();

        return new FullOrderDTO(order.getId(), itemDTOS, order.getTotal());
    }
}
