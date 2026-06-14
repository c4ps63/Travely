package com.travely.tour_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKeypointRequest {
    private Double latitude;
    private Double longitude;
    private String name;
    private String description;
    private String image;
}
