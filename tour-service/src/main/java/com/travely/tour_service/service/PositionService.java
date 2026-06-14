package com.travely.tour_service.service;

import com.travely.tour_service.dto.SetPositionRequest;
import com.travely.tour_service.model.TouristPosition;
import com.travely.tour_service.repository.TouristPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final TouristPositionRepository positionRepository;

    public TouristPosition setPosition(String username, SetPositionRequest request) {
        TouristPosition position = positionRepository.findByUsername(username)
                .orElse(TouristPosition.builder().username(username).build());

        position.setLatitude(request.getLatitude());
        position.setLongitude(request.getLongitude());
        position.setUpdatedAt(LocalDateTime.now());

        return positionRepository.save(position);
    }

    public TouristPosition getPosition(String username) {
        return positionRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Pozicija nije definisana."));
    }
}
