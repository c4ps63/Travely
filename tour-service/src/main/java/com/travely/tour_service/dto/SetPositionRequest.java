package com.travely.tour_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetPositionRequest {
    private Double latitude;
    private Double longitude;
}
