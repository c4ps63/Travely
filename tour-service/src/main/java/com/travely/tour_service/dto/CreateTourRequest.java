package com.travely.tour_service.dto;

import com.travely.tour_service.model.TourDifficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTourRequest {
    private String name;
    private String description;
    private TourDifficulty difficulty;
    private List<String> tags;
}
