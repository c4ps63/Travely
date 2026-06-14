package com.travely.tour_service.repository;

import com.travely.tour_service.model.Keypoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeypointRepository extends JpaRepository<Keypoint, Long> {
    List<Keypoint> findByTourId(Long tourId);
}
