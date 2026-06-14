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
}
