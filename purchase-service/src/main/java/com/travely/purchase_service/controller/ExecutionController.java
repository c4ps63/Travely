package com.travely.purchase_service.controller;

import com.travely.purchase_service.dto.StartExecutionRequest;
import com.travely.purchase_service.model.TourExecution;
import com.travely.purchase_service.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final ExecutionService executionService;

    @PostMapping
    public ResponseEntity<TourExecution> startExecution(
            @RequestHeader("X-Username") String username,
            @RequestBody StartExecutionRequest request) {
        return ResponseEntity.ok(executionService.startExecution(username, request.getTourId()));
    }

    @PostMapping("/{id}/check-position")
    public ResponseEntity<TourExecution> checkPosition(
            @RequestHeader("X-Username") String username,
            @PathVariable Long id) {
        return ResponseEntity.ok(executionService.checkPosition(username, id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TourExecution> complete(
            @RequestHeader("X-Username") String username,
            @PathVariable Long id) {
        return ResponseEntity.ok(executionService.completeExecution(username, id));
    }

    @PutMapping("/{id}/abandon")
    public ResponseEntity<TourExecution> abandon(
            @RequestHeader("X-Username") String username,
            @PathVariable Long id) {
        return ResponseEntity.ok(executionService.abandonExecution(username, id));
    }
}
