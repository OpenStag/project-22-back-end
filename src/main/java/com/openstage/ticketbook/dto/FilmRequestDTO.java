package com.openstage.ticketbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmRequestDTO {
    // Film details (Adding film information)
    @NotBlank(message = "Film title is required")
    private String filmTitle;

    @NotBlank(message = "Film description is required")
    private String filmDescription;

    @NotNull(message = "Film date is required")
    private LocalDate filmDate;

    @NotNull(message = "Film time is required")
    private LocalTime filmTime;

    private String imageUrl;

    // Price details (Adding ticket prices for the film)
    @NotNull(message = "Level 1 price is required")
    private Double l1Price;

    @NotNull(message = "Level 2 price is required")
    private Double l2Price;

    @NotNull(message = "Box price is required")
    private Double boxPrice;
}
