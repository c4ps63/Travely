package com.travely.tour_service.service;

import com.travely.tour_service.dto.CreateTourRequest;
import com.travely.tour_service.model.Tour;
import com.travely.tour_service.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    public Tour createTour(String authorUsername, CreateTourRequest request) {
        Tour tour = Tour.builder()
                .authorUsername(authorUsername)
                .name(request.getName())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .tags(request.getTags())
                .build();
        return tourRepository.save(tour);
    }

    public List<Tour> getMyTours(String authorUsername) {
        return tourRepository.findByAuthorUsername(authorUsername);
    }
}
