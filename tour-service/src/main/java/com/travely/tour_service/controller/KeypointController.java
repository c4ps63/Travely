package com.travely.tour_service.controller;

import com.travely.tour_service.dto.CreateKeypointRequest;
import com.travely.tour_service.model.Keypoint;
import com.travely.tour_service.service.KeypointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours/{tourId}/keypoints")
@RequiredArgsConstructor
public class KeypointController {

    private final KeypointService keypointService;

    @PostMapping
    public ResponseEntity<Keypoint> addKeypoint(
            @PathVariable Long tourId,
            @RequestBody CreateKeypointRequest request) {
        return ResponseEntity.ok(keypointService.addKeypoint(tourId, request));
    }

    @GetMapping
    public ResponseEntity<List<Keypoint>> getKeypoints(@PathVariable Long tourId) {
        return ResponseEntity.ok(keypointService.getKeypoints(tourId));
    }
}
