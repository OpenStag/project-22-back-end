package com.openstage.ticketbook.repo;

import com.openstage.ticketbook.model.Film;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<@NonNull Film, @NonNull Long> {
}
