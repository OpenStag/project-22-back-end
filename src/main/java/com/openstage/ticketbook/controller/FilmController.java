package com.openstage.ticketbook.controller;

import com.openstage.ticketbook.dto.FilmRequestDTO;
import com.openstage.ticketbook.dto.FilmResponseDTO;
import com.openstage.ticketbook.dto.ResponseDTO;
import com.openstage.ticketbook.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
@Tag(name = "Film Controller", description = "Endpoints for managing films")
public class FilmController {
    private final FilmService filmService;

    // Add a new Film
    @PostMapping
    @Operation(summary = "Add a new film")
    public ResponseEntity<@NonNull ResponseDTO<FilmResponseDTO>> addFilm(
            @Valid @RequestBody FilmRequestDTO request
    ) {
        FilmResponseDTO response = filmService.addFilm(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO<>(true, "Film added successfully", response)
        );
    }

    // Get Upcoming Films
    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming films")
    public ResponseEntity<@NonNull ResponseDTO<List<FilmResponseDTO>>> getUpcomingFilms() {
        List<FilmResponseDTO> films = filmService.getUpcomingFilms();
        return ResponseEntity.ok(
                new ResponseDTO<>(true, "Upcoming films retrieved successfully", films)
        );
    }

    // Get All Films
    @GetMapping
    @Operation(summary = "Get all films")
    public ResponseEntity<@NonNull ResponseDTO<List<FilmResponseDTO>>> getAllFilms() {
        List<FilmResponseDTO> films = filmService.getAllFilms();
        return ResponseEntity.ok(
                new ResponseDTO<>(true, "All films retrieved successfully", films)
        );
    }
}
