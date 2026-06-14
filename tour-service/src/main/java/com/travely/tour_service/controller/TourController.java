package com.travely.tour_service.controller;

import com.travely.tour_service.dto.CreateTourRequest;
import com.travely.tour_service.model.Tour;
import com.travely.tour_service.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @PostMapping
    public ResponseEntity<Tour> createTour(
            @RequestHeader("X-Username") String username,
            @RequestBody CreateTourRequest request) {
        return ResponseEntity.ok(tourService.createTour(username, request));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Tour>> getMyTours(
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(tourService.getMyTours(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTour(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.getTour(id));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<Tour> publishTour(
            @PathVariable Long id,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(tourService.publishTour(id, username));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Tour> archiveTour(
            @PathVariable Long id,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(tourService.archiveTour(id, username));
    }
}
