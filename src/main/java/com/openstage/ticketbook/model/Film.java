package com.openstage.ticketbook.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_name", nullable = false)
    private String filmTitle;

    @Column(name = "film_date", nullable = false)
    private LocalDate filmDate;

    @Column(name = "film_time", nullable = false)
    private LocalTime filmTime;

    @Column(name = "image", nullable = false)
    private String imageUrl = "https://placehold.co/200x300"; // Placeholder image URL

    // --- Relationships ---
    // One-to-One relationship with Pricing
    @OneToOne(mappedBy = "film", cascade = CascadeType.ALL)
    private Pricing pricing;

    // One-to-One relationship with Seat
    @OneToOne(mappedBy = "film", cascade = CascadeType.ALL)
    private Seat seat;
}
