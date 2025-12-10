package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.FilmRequestDTO;
import com.openstage.ticketbook.dto.FilmResponseDTO;
import com.openstage.ticketbook.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
@Tag(name = "Film Controller", description = "Endpoints for managing films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @Operation(summary = "Add a new film")
    public ResponseEntity<@NonNull FilmResponseDTO> addFilm(
            @Valid @RequestBody FilmRequestDTO request
    ) {
        FilmResponseDTO response = filmService.addFilm(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
