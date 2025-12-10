package com.openstage.ticketbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SQL: BIGINT AUTO_INCREMENT PRIMARY KEY
    private Long id;
    
    @Column(nullable = false)
    private String role; // e.g., "ROLE_USER" or "ROLE_ADMIN"
    
    // SQL: VARCHAR(50) NOT NULL
    @Column(length = 50, nullable = false)
    private String username;
    
    // SQL: VARCHAR(100) NOT NULL UNIQUE
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    
    // SQL: VARCHAR(255) NOT NULL
    @Column(nullable = false)
    private String password;
    
    // SQL: BOOLEAN DEFAULT TRUE
    // initialize it to 'true' in Java so the first insert is correct.
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    // SQL: TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @CreationTimestamp // Hibernate handles filling this automatically
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // SQL: TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    @UpdateTimestamp // Hibernate updates this automatically whenever save
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}