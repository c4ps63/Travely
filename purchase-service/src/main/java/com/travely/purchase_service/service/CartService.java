package com.travely.purchase_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.travely.purchase_service.client.TourClient;
import com.travely.purchase_service.model.OrderItem;
import com.travely.purchase_service.model.ShoppingCart;
import com.travely.purchase_service.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShoppingCartRepository cartRepository;
    private final TourClient tourClient;

    public ShoppingCart getOrCreateCart(String username) {
        return cartRepository.findByTouristUsername(username)
                .orElseGet(() -> cartRepository.save(
                        ShoppingCart.builder()
                                .touristUsername(username)
                                .totalPrice(0.0)
                                .build()
                ));
    }

    public ShoppingCart addItem(String username, Long tourId) {
        JsonNode tour = tourClient.getTour(tourId);
        if (tour == null) {
            throw new IllegalArgumentException("Tura nije pronađena.");
        }

        String status = tour.get("status").asText();
        if ("ARCHIVED".equals(status)) {
            throw new IllegalArgumentException("Arhivirana tura se ne može kupiti.");
        }
        if (!"PUBLISHED".equals(status)) {
            throw new IllegalArgumentException("Tura nije objavljena.");
        }

        ShoppingCart cart = getOrCreateCart(username);

        boolean alreadyInCart = cart.getItems().stream()
                .anyMatch(i -> i.getTourId().equals(tourId));
        if (alreadyInCart) {
            throw new IllegalArgumentException("Tura je već u korpi.");
        }

        OrderItem item = OrderItem.builder()
                .cart(cart)
                .tourId(tourId)
                .tourName(tour.get("name").asText())
                .price(tour.get("price").asDouble())
                .build();

        cart.getItems().add(item);
        cart.recalculateTotal();
        return cartRepository.save(cart);
    }

    public ShoppingCart removeItem(String username, Long tourId) {
        ShoppingCart cart = getOrCreateCart(username);
        cart.getItems().removeIf(i -> i.getTourId().equals(tourId));
        cart.recalculateTotal();
        return cartRepository.save(cart);
    }
}
