package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.ResponseDTO;
import com.openstage.ticketbook.dto.UserRequestDTO;
import com.openstage.ticketbook.dto.UserResponseDTO;
import com.openstage.ticketbook.service.AuthService;
import com.openstage.ticketbook.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Get all users", description = "Get a list of all users. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers(HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDTO<>(false, "Access denied", null));
        }
        return ResponseEntity.ok(new ResponseDTO<>(true, "Users retrieved successfully", userService.getAllUsers()));
    }

    @Operation(summary = "Get user by ID", description = "Get a user by their ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserById(@PathVariable Long id, HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDTO<>(false, "Access denied", null));
        }
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ResponseDTO<>(true, "User found", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(false, "User not found", null)));
    }

    @Operation(summary = "Update user by ID", description = "Update a user by their ID. Requires ADMIN role or for the user to be updating their own profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN") && !authService.isSameUser(request, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDTO<>(false, "Access denied", null));
        }
        return userService.updateUser(id, userRequestDTO)
                .map(user -> ResponseEntity.ok(new ResponseDTO<>(true, "User updated successfully", user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(false, "User not found", null)));
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID. Requires ADMIN role or for the user to be deleting their own profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN") && !authService.isSameUser(request, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDTO<>(false, "Access denied", null));
        }
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok(new ResponseDTO<>(true, "User deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>(false, "User not found", null));
    }

    @Operation(summary = "Get user profile", description = "A test endpoint to get the user's profile. Requires USER role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile accessed successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
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
