package com.openstage.ticketbook.service;

import com.openstage.ticketbook.dto.LoginRequestDTO;
import com.openstage.ticketbook.dto.LoginResponseDTO;
import com.openstage.ticketbook.dto.UserRequestDTO;
import com.openstage.ticketbook.dto.UserResponseDTO;
import com.openstage.ticketbook.model.User;
import com.openstage.ticketbook.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRequestDTO request) {

        // 1. Validate
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // 2. Map Request DTO -> Model
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setActive(true);
        newUser.setRole("ROLE_USER");
        // SECURITY: Force everyone to be a normal user
        // Note: createdAt is handled by @CreationTimestamp in the model

        // 3. Save to DB
        User savedUser = userRepository.save(newUser);

        //log
        log.info("Added new film: {}", newUser.getUsername());

        // 4. Map Model -> Response DTO
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.isActive(),
                savedUser.getCreatedAt()
        );
    }

    // Login User
    public LoginResponseDTO loginUser(LoginRequestDTO request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: Invalid Email or Password."));

        // 2. Check if user is active
        if (!user.isActive()) {
            throw new RuntimeException("Error: User account is inactive.");
        }

        // 3. Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Error: Invalid Email or Password.");
        }

        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}