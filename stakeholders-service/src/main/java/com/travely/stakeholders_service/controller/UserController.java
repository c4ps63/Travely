package com.travely.stakeholders_service.controller;

import com.travely.stakeholders_service.dto.UpdateProfileRequest;
import com.travely.stakeholders_service.dto.UserProfileResponse;
import com.travely.stakeholders_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getMyProfile(userDetails.getUsername()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                               @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateMyProfile(userDetails.getUsername(), request));
    }
}
