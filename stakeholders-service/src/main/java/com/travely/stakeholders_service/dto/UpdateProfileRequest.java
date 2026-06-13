package com.travely.stakeholders_service.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String surname;
    private String profileImage;
    private String biography;
    private String motto;
}
