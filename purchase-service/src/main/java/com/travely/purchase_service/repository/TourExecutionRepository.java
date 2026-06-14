package com.travely.purchase_service.repository;

import com.travely.purchase_service.model.TourExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourExecutionRepository extends JpaRepository<TourExecution, Long> {
    List<TourExecution> findByTouristUsername(String touristUsername);
}
