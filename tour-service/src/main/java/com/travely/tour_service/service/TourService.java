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

    public Tour getTour(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tura nije pronađena."));
    }

    public Tour publishTour(Long id, String username) {
        Tour tour = getTour(id);
        if (!tour.getAuthorUsername().equals(username)) {
            throw new IllegalArgumentException("Nemate pravo da objavljujete ovu turu.");
        }
        tour.setStatus(com.travely.tour_service.model.TourStatus.PUBLISHED);
        return tourRepository.save(tour);
    }

    public Tour archiveTour(Long id, String username) {
        Tour tour = getTour(id);
        if (!tour.getAuthorUsername().equals(username)) {
            throw new IllegalArgumentException("Nemate pravo da arhivirate ovu turu.");
        }
        tour.setStatus(com.travely.tour_service.model.TourStatus.ARCHIVED);
        return tourRepository.save(tour);
    }
}
