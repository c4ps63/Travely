package com.travely.stakeholders_service.dto;

import com.travely.stakeholders_service.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
