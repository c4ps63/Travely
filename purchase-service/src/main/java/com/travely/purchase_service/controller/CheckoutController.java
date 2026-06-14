package com.travely.purchase_service.controller;

import com.travely.purchase_service.model.TourPurchaseToken;
import com.travely.purchase_service.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<List<TourPurchaseToken>> checkout(
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(checkoutService.checkout(username));
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<TourPurchaseToken>> getMyPurchases(
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(checkoutService.getMyTokens(username));
    }
}
