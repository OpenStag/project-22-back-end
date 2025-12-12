package com.openstage.ticketbook.repo;

import com.openstage.ticketbook.model.Film;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<@NonNull Film, @NonNull Long> {
    // Find films with filmDate greater than or equal to currentDate, ordered by filmDate and filmTime
    // 1st - Find By filmDate Greater Than Equal to currentDate
    // 2nd - Order By filmDate Asc filmTime Asc
    @NonNull
    List<@NonNull Film> findByFilmDateGreaterThanEqualOrderByFilmDateAscFilmTimeAsc(LocalDate currentDate);
}
