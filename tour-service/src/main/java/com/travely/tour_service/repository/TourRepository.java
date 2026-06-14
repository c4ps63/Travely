package com.travely.tour_service.repository;

import com.travely.tour_service.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByAuthorUsername(String authorUsername);
}
