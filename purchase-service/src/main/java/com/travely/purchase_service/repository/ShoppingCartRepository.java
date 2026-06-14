package com.travely.purchase_service.repository;

import com.travely.purchase_service.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByTouristUsername(String touristUsername);
}
