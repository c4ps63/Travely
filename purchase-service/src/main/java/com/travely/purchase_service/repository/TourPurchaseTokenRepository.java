package com.travely.purchase_service.repository;

import com.travely.purchase_service.model.TourPurchaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourPurchaseTokenRepository extends JpaRepository<TourPurchaseToken, Long> {
    Optional<TourPurchaseToken> findByTouristUsernameAndTourId(String touristUsername, Long tourId);
    List<TourPurchaseToken> findByTouristUsername(String touristUsername);
}
