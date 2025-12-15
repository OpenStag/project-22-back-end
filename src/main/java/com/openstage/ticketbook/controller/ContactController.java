package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.ContactDTO;
import com.openstage.ticketbook.model.Contact;
import com.openstage.ticketbook.service.AuthService;
import com.openstage.ticketbook.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Contact management APIs")
public class ContactController {
    private final ContactService contactService;
    private final AuthService authService;

    @Operation(summary = "Create a new contact", description = "Create a new contact with the given details. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO, HttpServletRequest request) {
        if (!authService.isUserAlreadyLoggedIn(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        return ResponseEntity.ok(contactService.createContact(contactDTO));
    }

    @Operation(summary = "Get all contacts", description = "Get a list of all contacts. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public ResponseEntity<?> getAllContacts(HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @Operation(summary = "Delete a contact", description = "Delete a contact by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contact deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id, HttpServletRequest request) {
        if (!authService.hasRole(request, "ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
