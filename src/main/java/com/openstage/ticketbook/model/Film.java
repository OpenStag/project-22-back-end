package com.openstage.ticketbook.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_name", nullable = false)
    private String filmTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String filmDescription;

    @Column(name = "film_date" , nullable = false)
    private LocalDate filmDate;

    @Column(name = "film_time", nullable = false)
    private LocalTime filmTime;

    @Column(name = "image_url", nullable = false)
    private String imageUrl = "https://placehold.co/200x300"; // Placeholder image URL
}
