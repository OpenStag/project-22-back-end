package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.*;
import com.openstage.ticketbook.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Auth Controller", description = "Endpoints for user authentication")
public class AuthController {
    private final AuthService authService;

    // Signup Endpoint
    @PostMapping("/signup")
    @Operation(summary = "Add a new User")
    public ResponseEntity<@NonNull ResponseDTO<UserResponseDTO>> signup(
            @RequestBody @Valid UserRequestDTO request
    ) {
        try {
            // Success: Return 201 Created and the JSON Data
            UserResponseDTO response = authService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseDTO<>(true, "User registered successfully", response)
            );

        } catch (RuntimeException e) {
            // Error: Return 400 Bad Request and the error message string
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>(false, e.getMessage(), null)
            );
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    @Operation(summary = "Login a User")
    public ResponseEntity<@NonNull ResponseDTO<LoginResponseDTO>> login(
            @RequestBody @Valid LoginRequestDTO request,
            HttpServletRequest httpServletRequest) {

        try {
            // Check if user already logged in
            if (authService.isUserAlreadyLoggedIn(httpServletRequest)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseDTO<>(false, "User already logged in", null)
                );
            }

            LoginResponseDTO response = authService.loginUser(request);

            // Create a session
            HttpSession newSession = httpServletRequest.getSession(true);
            newSession.setAttribute("USER_ID", response.getId());
            newSession.setAttribute("USERNAME", response.getUsername());
            newSession.setAttribute("EMAIL", response.getEmail());
            newSession.setAttribute("ROLE", response.getRole());

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO<>(true, "Login successful", response)
            );

        } catch (RuntimeException e) {
            // need to send unauthorized status with message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDTO<>(false, e.getMessage(), null)
            );
        }
    }

    // Logout Endpoint
    @PostMapping("/logout")
    @Operation(summary = "Logout a User")
    public ResponseEntity<@NonNull ResponseDTO<String>> logout(
            HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            // Already logged out
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>(false, "No active session found", null)
            );
        }
        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(true, "Logout successful", null)
        );
    }
}