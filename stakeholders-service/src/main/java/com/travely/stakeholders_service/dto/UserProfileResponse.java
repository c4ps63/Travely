package com.travely.stakeholders_service.dto;

import com.travely.stakeholders_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String name;
    private String surname;
    private String profileImage;
    private String biography;
    private String motto;
}
