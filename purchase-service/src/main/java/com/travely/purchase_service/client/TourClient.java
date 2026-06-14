package com.travely.purchase_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TourClient {

    @Value("${tour.service.url}")
    private String tourServiceUrl;

    private final RestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    public Map<String, Object> getTour(Long tourId) {
        return restTemplate.getForObject(tourServiceUrl + "/tours/" + tourId, Map.class);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getKeypoints(Long tourId) {
        Map[] keypoints = restTemplate.getForObject(
                tourServiceUrl + "/tours/" + tourId + "/keypoints", Map[].class);
        if (keypoints == null) return List.of();
        return Arrays.stream(keypoints).map(m -> (Map<String, Object>) m).toList();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getPosition(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Username", username);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                tourServiceUrl + "/position",
                HttpMethod.GET,
                entity,
                Map.class);
        return response.getBody();
    }
}
