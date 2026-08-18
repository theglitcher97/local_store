package com.store.local_store.web.rest;

import com.store.local_store.application.model.AddProductToCartCommand;
import com.store.local_store.application.model.RemoveProductFromCartCommand;
import com.store.local_store.application.use_cases.CartUseCases;
import com.store.local_store.web.dtos.CartDTO;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/cart")
@AllArgsConstructor
public class CartRestController {
    private CartUseCases cartUseCases;

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@AuthenticationPrincipal String userId, @PathParam("productId") Long productId) {
        this.cartUseCases.addProduct(new AddProductToCartCommand(Long.parseLong(userId), productId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@AuthenticationPrincipal String userId) {
        this.cartUseCases.checkout(Long.parseLong(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal String userId) {
        CartDTO cartDTO = this.cartUseCases.getCart(Long.parseLong(userId));
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> removeProduct(@AuthenticationPrincipal String userId,
                                              @PathParam("productId") Long productId,
                                              @PathParam("removeAll") Boolean removeAll) {
        this.cartUseCases.removeProduct(new RemoveProductFromCartCommand(Long.parseLong(userId), productId, removeAll));
        return ResponseEntity.noContent().build();
    }
}
