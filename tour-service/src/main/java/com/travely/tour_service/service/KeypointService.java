package com.travely.tour_service.service;

import com.travely.tour_service.dto.CreateKeypointRequest;
import com.travely.tour_service.model.Keypoint;
import com.travely.tour_service.repository.KeypointRepository;
import com.travely.tour_service.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeypointService {

    private final KeypointRepository keypointRepository;
    private final TourRepository tourRepository;

    public Keypoint addKeypoint(Long tourId, CreateKeypointRequest request) {
        tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tura nije pronađena."));

        Keypoint keypoint = Keypoint.builder()
                .tourId(tourId)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .build();
        return keypointRepository.save(keypoint);
    }

    public List<Keypoint> getKeypoints(Long tourId) {
        return keypointRepository.findByTourId(tourId);
    }
}
