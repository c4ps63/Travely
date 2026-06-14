package com.travely.tour_service.repository;

import com.travely.tour_service.model.TouristPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TouristPositionRepository extends JpaRepository<TouristPosition, Long> {
    Optional<TouristPosition> findByUsername(String username);
}
