package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.ResponseDTO;
import com.openstage.ticketbook.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {
    private final AuthService authService;

    // Test Endpoint
    @GetMapping("/profile")
    public ResponseEntity<@NonNull ResponseDTO<String>> getUserProfile(HttpServletRequest request) {
        // Check role from session
        if(!authService.hasRole(request, "ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDTO<>(
                            false,
                            "Access denied: User role required",
                            null
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(
                        true,
                        "User profile accessed successfully",
                        "This is a protected user profile data."
                )
        );
    }
}
