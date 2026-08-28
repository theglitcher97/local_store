package com.store.local_store.web.rest;

import com.store.local_store.application.use_cases.OrderUseCases;
import com.store.local_store.web.dtos.BasicOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderRestController {
    private OrderUseCases orderUseCases;

    @GetMapping
    public ResponseEntity<List<BasicOrderDTO>> findAllOrders(@AuthenticationPrincipal String userId) {
        List<BasicOrderDTO> orders = this.orderUseCases.findAllOrder(Long.parseLong(userId));
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
