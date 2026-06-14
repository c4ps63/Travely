package com.travely.purchase_service.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TourClient {

    @Value("${tour.service.url}")
    private String tourServiceUrl;

    private final RestTemplate restTemplate;

    public JsonNode getTour(Long tourId) {
        return restTemplate.getForObject(tourServiceUrl + "/tours/" + tourId, JsonNode.class);
    }

    public List<JsonNode> getKeypoints(Long tourId) {
        JsonNode[] keypoints = restTemplate.getForObject(
                tourServiceUrl + "/tours/" + tourId + "/keypoints", JsonNode[].class);
        return keypoints != null ? Arrays.asList(keypoints) : List.of();
    }

    public JsonNode getPosition(String username) {
        var headers = new org.springframework.http.HttpHeaders();
        headers.set("X-Username", username);
        var entity = new org.springframework.http.HttpEntity<>(headers);
        return restTemplate.exchange(
                tourServiceUrl + "/position",
                org.springframework.http.HttpMethod.GET,
                entity,
                JsonNode.class).getBody();
    }
}
