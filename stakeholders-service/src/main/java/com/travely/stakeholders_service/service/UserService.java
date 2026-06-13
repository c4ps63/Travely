package com.travely.stakeholders_service.service;

import com.travely.stakeholders_service.dto.UpdateProfileRequest;
import com.travely.stakeholders_service.dto.UserProfileResponse;
import com.travely.stakeholders_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getMyProfile(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen."));

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .surname(user.getSurname())
                .profileImage(user.getProfileImage())
                .biography(user.getBiography())
                .motto(user.getMotto())
                .build();
    }

    public UserProfileResponse updateMyProfile(String username, UpdateProfileRequest request) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen."));

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setProfileImage(request.getProfileImage());
        user.setBiography(request.getBiography());
        user.setMotto(request.getMotto());

        userRepository.save(user);

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .name(user.getName())
                .surname(user.getSurname())
                .profileImage(user.getProfileImage())
                .biography(user.getBiography())
                .motto(user.getMotto())
                .build();
    }
}
