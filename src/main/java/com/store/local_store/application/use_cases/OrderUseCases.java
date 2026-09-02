package com.store.local_store.application.use_cases;

import com.store.local_store.application.model.CancelOrderCommand;
import com.store.local_store.application.model.PayOrderCommand;
import com.store.local_store.domain.enums.OrderState;
import com.store.local_store.domain.enums.PaymentStatus;
import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.Payment;
import com.store.local_store.domain.services.OrderService;
import com.store.local_store.domain.services.PaymentService;
import com.store.local_store.domain.services.ProductService;
import com.store.local_store.web.dtos.BasicOrderDTO;
import com.store.local_store.web.dtos.FullOrderDTO;
import com.store.local_store.web.dtos.OrderItemDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class OrderUseCases {
    private OrderService orderService;
    private ProductService productService;
    private PaymentService paymentService;

    public List<BasicOrderDTO> findOrders(long userId, String state) {
        List<Order> orders;
        if (Objects.isNull(state) || state.equals("ALL"))
            orders = this.orderService.findAll(userId);
        else {
            try {
                orders = this.orderService.findAll(userId, OrderState.valueOf(state));
            } catch (IllegalArgumentException e) {
                log.info("Invalid state: "+state+"; searching for all orders");
                orders = this.orderService.findAll(userId);
            }
        }
        return orders.stream()
                .map(order -> new BasicOrderDTO(order.getId(), order.getItems().size(), order.getTotal(), order.getState()))
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

    @Transactional
    public void cancelOrder(CancelOrderCommand command) {
        Order order = this.orderService.findOrder(command.orderId(), command.userId());
        order.cancel();
        this.orderService.save(order);
        this.productService.releaseReservation(order);
    }

    @Transactional
    public void payOrder(PayOrderCommand command) {
        Order order = this.orderService.findOrder(command.orderId(), command.userId());
        order.validatePayment();

        Payment payment = this.paymentService.pay(order, command.method());
        if (payment.getStatus() == PaymentStatus.SUCCESS){
            order.complete();
            this.orderService.save(order);
            this.productService.completeReservation(order);
        }
    }
}
