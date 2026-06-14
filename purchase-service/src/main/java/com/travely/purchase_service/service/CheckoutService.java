package com.travely.purchase_service.service;

import com.travely.purchase_service.model.ShoppingCart;
import com.travely.purchase_service.model.TourPurchaseToken;
import com.travely.purchase_service.repository.ShoppingCartRepository;
import com.travely.purchase_service.repository.TourPurchaseTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ShoppingCartRepository cartRepository;
    private final TourPurchaseTokenRepository tokenRepository;

    public List<TourPurchaseToken> checkout(String username) {
        ShoppingCart cart = cartRepository.findByTouristUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Korpa nije pronađena."));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Korpa je prazna.");
        }

        List<TourPurchaseToken> tokens = cart.getItems().stream()
                .map(item -> TourPurchaseToken.builder()
                        .touristUsername(username)
                        .tourId(item.getTourId())
                        .tourName(item.getTourName())
                        .purchasedAt(LocalDateTime.now())
                        .build())
                .toList();

        tokenRepository.saveAll(tokens);
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return tokens;
    }

    public List<TourPurchaseToken> getMyTokens(String username) {
        return tokenRepository.findByTouristUsername(username);
    }

    public boolean hasPurchased(String username, Long tourId) {
        return tokenRepository.findByTouristUsernameAndTourId(username, tourId).isPresent();
    }
}
