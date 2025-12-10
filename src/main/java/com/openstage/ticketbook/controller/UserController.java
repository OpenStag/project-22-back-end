package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.UserRequestDTO;
import com.openstage.ticketbook.dto.UserResponseDTO;
import com.openstage.ticketbook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Controller", description = "Endpoints for managing Users")
public class UserController {
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
}