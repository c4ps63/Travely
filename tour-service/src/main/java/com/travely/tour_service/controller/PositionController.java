package com.travely.tour_service.controller;

import com.travely.tour_service.dto.SetPositionRequest;
import com.travely.tour_service.model.TouristPosition;
import com.travely.tour_service.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<TouristPosition> setPosition(
            @RequestHeader("X-Username") String username,
            @RequestBody SetPositionRequest request) {
        return ResponseEntity.ok(positionService.setPosition(username, request));
    }

    @GetMapping
    public ResponseEntity<TouristPosition> getPosition(
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(positionService.getPosition(username));
    }
}
