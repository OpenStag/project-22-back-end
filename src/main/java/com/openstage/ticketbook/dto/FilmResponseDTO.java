package com.openstage.ticketbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmResponseDTO {
    // Film details (Showing film information)
    private Long id;
    private String filmTitle;
    private String filmDescription;
    private String filmDate;
    private String filmTime;
    private String imageUrl;

    // Price details (Showing ticket prices)
    private Double l1Price;
    private Double l2Price;
    private Double boxPrice;

    // Seat details (Showing available seats)
    private Integer level1Seats;
    private Integer level2Seats;
    private Integer boxSeats;
}
