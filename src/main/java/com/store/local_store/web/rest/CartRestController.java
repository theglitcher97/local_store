package com.store.local_store.web.rest;

import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.application.use_cases.CartUseCases;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
@AllArgsConstructor
public class CartRestController {
    private CartUseCases cartUseCases;

    @PostMapping
    public ResponseEntity<Void> addProduct(@AuthenticationPrincipal String userId, @PathParam("productId") Long productId) {
        this.cartUseCases.addProduct(new AddProductToCartCommand(Long.parseLong(userId), productId));
        return ResponseEntity.noContent().build();
    }
}
