package com.travely.purchase_service.controller;

import com.travely.purchase_service.dto.AddToCartRequest;
import com.travely.purchase_service.model.ShoppingCart;
import com.travely.purchase_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ShoppingCart> getCart(@RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(cartService.getOrCreateCart(username));
    }

    @PostMapping("/items")
    public ResponseEntity<ShoppingCart> addItem(
            @RequestHeader("X-Username") String username,
            @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addItem(username, request.getTourId()));
    }

    @DeleteMapping("/items/{tourId}")
    public ResponseEntity<ShoppingCart> removeItem(
            @RequestHeader("X-Username") String username,
            @PathVariable Long tourId) {
        return ResponseEntity.ok(cartService.removeItem(username, tourId));
    }
}
