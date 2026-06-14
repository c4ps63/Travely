package com.travely.purchase_service.service;

import com.travely.purchase_service.client.TourClient;
import com.travely.purchase_service.model.CompletedKeypoint;
import com.travely.purchase_service.model.ExecutionStatus;
import com.travely.purchase_service.model.TourExecution;
import com.travely.purchase_service.repository.TourExecutionRepository;
import com.travely.purchase_service.repository.TourPurchaseTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExecutionService {

    private final TourExecutionRepository executionRepository;
    private final TourPurchaseTokenRepository tokenRepository;
    private final TourClient tourClient;

    private static final double PROXIMITY_METERS = 100.0;

    public TourExecution startExecution(String username, Long tourId) {
        tokenRepository.findByTouristUsernameAndTourId(username, tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tura nije kupljena."));

        TourExecution execution = TourExecution.builder()
                .touristUsername(username)
                .tourId(tourId)
                .status(ExecutionStatus.ACTIVE)
                .startedAt(LocalDateTime.now())
                .lastActivity(LocalDateTime.now())
                .build();

        return executionRepository.save(execution);
    }

    public TourExecution checkPosition(String username, Long executionId) {
        TourExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Sesija nije pronađena."));

        if (execution.getStatus() != ExecutionStatus.ACTIVE) {
            throw new IllegalArgumentException("Sesija nije aktivna.");
        }

        Map<String, Object> position = tourClient.getPosition(username);
        if (position == null || position.containsKey("error")) {
            execution.setLastActivity(LocalDateTime.now());
            return executionRepository.save(execution);
        }

        double lat = ((Number) position.get("latitude")).doubleValue();
        double lng = ((Number) position.get("longitude")).doubleValue();

        List<Map<String, Object>> keypoints = tourClient.getKeypoints(execution.getTourId());

        for (Map<String, Object> kp : keypoints) {
            Long kpId = ((Number) kp.get("id")).longValue();
            boolean alreadyCompleted = execution.getCompletedKeypoints().stream()
                    .anyMatch(ck -> ck.getKeypointId().equals(kpId));
            if (alreadyCompleted) continue;

            double kpLat = ((Number) kp.get("latitude")).doubleValue();
            double kpLng = ((Number) kp.get("longitude")).doubleValue();

            if (distanceMeters(lat, lng, kpLat, kpLng) <= PROXIMITY_METERS) {
                CompletedKeypoint completed = CompletedKeypoint.builder()
                        .execution(execution)
                        .keypointId(kpId)
                        .completedAt(LocalDateTime.now())
                        .build();
                execution.getCompletedKeypoints().add(completed);
            }
        }

        execution.setLastActivity(LocalDateTime.now());
        return executionRepository.save(execution);
    }

    public TourExecution completeExecution(String username, Long executionId) {
        TourExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Sesija nije pronađena."));
        execution.setStatus(ExecutionStatus.COMPLETED);
        execution.setEndedAt(LocalDateTime.now());
        return executionRepository.save(execution);
    }

    public TourExecution abandonExecution(String username, Long executionId) {
        TourExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Sesija nije pronađena."));
        execution.setStatus(ExecutionStatus.ABANDONED);
        execution.setEndedAt(LocalDateTime.now());
        return executionRepository.save(execution);
    }

    private double distanceMeters(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
