package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.*;
import com.openstage.ticketbook.service.UserService;
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
@Tag(name = "User Controller", description = "Endpoints for managing Users")
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "Add a new User")
    public ResponseEntity<?> signup(
            @RequestBody @Valid UserRequestDTO request
    ) {
        try {
            // Success: Return 201 Created and the JSON Data
            UserResponseDTO response = userService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Error: Return 400 Bad Request and the error message string
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    @Operation(summary = "Login a User")
    public ResponseEntity<@NonNull ResponseDTO<LoginResponseDTO>> login(
            @RequestBody @Valid LoginRequestDTO request,
            HttpServletRequest httpServletRequest) {

        try {
            LoginResponseDTO response = userService.loginUser(request);

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
}