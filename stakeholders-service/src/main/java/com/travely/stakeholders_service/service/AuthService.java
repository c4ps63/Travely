package com.travely.stakeholders_service.service;

import com.travely.stakeholders_service.dto.AuthResponse;
import com.travely.stakeholders_service.dto.LoginRequest;
import com.travely.stakeholders_service.dto.RegisterRequest;
import com.travely.stakeholders_service.model.Role;
import com.travely.stakeholders_service.model.User;
import com.travely.stakeholders_service.repository.UserRepository;
import com.travely.stakeholders_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Korisničko ime je već zauzeto.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email je već zauzet.");
        }
        if (request.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Nije moguće registrovati administratora.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen."));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token);
    }
}
