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
@Table(name = "pricing")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "l1_price", nullable = false)
    private Double level1Price;

    @Column(name = "l2_price", nullable = false)
    private Double level2Price;

    @Column(name = "box_price", nullable = false)
    private Double boxPrice;

    // --- Relationships ---
    // One-to-One relationship with Film
    @OneToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
}