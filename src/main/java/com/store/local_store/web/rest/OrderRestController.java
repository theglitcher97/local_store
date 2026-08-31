package com.store.local_store.web.rest;

import com.store.local_store.application.model.CancelOrderCommand;
import com.store.local_store.application.use_cases.OrderUseCases;
import com.store.local_store.web.dtos.BasicOrderDTO;
import com.store.local_store.web.dtos.FullOrderDTO;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderRestController {
    private OrderUseCases orderUseCases;

    @GetMapping
    public ResponseEntity<List<BasicOrderDTO>> findAllOrders(@AuthenticationPrincipal String userId, @PathParam("state") String state) {
        List<BasicOrderDTO> orders = this.orderUseCases.findOrders(Long.parseLong(userId), state);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullOrderDTO> findAllOrders(@AuthenticationPrincipal String userId, @PathVariable Long id) {
        FullOrderDTO order = this.orderUseCases.findOrder(id, Long.parseLong(userId));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> updateOrderState(@AuthenticationPrincipal String userId, @PathVariable Long id) {
       CancelOrderCommand command = new CancelOrderCommand(id, Long.parseLong(userId));
       this.orderUseCases.cancelOrder(command);
       return ResponseEntity.ok().build();
    }

}
