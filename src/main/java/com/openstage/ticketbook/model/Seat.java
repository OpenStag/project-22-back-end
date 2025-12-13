package com.openstage.ticketbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level1", nullable = false)
    private Integer level1Seats = 50;

    @Column(name = "level2", nullable = false)
    private Integer level2Seats = 50;

    @Column(name = "box", nullable = false)
    private Integer boxSeats = 10;

    // --- Relationships ---
    // One-to-One relationship with Film
    @OneToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
}
