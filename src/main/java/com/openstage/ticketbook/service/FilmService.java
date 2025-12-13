package com.openstage.ticketbook.service;

import com.openstage.ticketbook.dto.FilmRequestDTO;
import com.openstage.ticketbook.dto.FilmResponseDTO;
import com.openstage.ticketbook.model.Film;
import com.openstage.ticketbook.model.Pricing;
import com.openstage.ticketbook.model.Seat;
import com.openstage.ticketbook.repo.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    private final FilmRepository filmRepository;

    // Add a Film
    public FilmResponseDTO addFilm(FilmRequestDTO request) {
        Film newFilm = new Film();
        newFilm.setFilmTitle(request.getFilmTitle());
        newFilm.setFilmDate(request.getFilmDate());
        newFilm.setFilmTime(request.getFilmTime());
        newFilm.setImageUrl(
                request.getImageUrl() != null ? request.getImageUrl() : "https://placehold.co/200x300"
        );

        Pricing pricing = new Pricing();
        pricing.setLevel1Price(request.getL1Price());
        pricing.setLevel2Price(request.getL2Price());
        pricing.setBoxPrice(request.getBoxPrice());
        pricing.setFilm(newFilm);

        Seat seat = new Seat();
        seat.setFilm(newFilm);

        newFilm.setPricing(pricing);
        newFilm.setSeat(seat);

        filmRepository.save(newFilm);

        log.info("Added new film: {}", newFilm.getFilmTitle());

        return getFilmResponseDTO(newFilm, pricing, seat);
    }

    // Get Upcoming Films
    public List<FilmResponseDTO> getUpcomingFilms() {
        List<Film> films = filmRepository.findByFilmDateGreaterThanEqualOrderByFilmDateAscFilmTimeAsc(
                LocalDate.now()
        );

        return films.stream().map(
                film -> getFilmResponseDTO(
                        film,
                        film.getPricing(),
                        film.getSeat()
                )
        ).toList();
    }

    // Get All Films
    public List<FilmResponseDTO> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        return films.stream().map(
                film -> getFilmResponseDTO(
                        film,
                        film.getPricing(),
                        film.getSeat()
                )
        ).toList();
    }

    // Get Film by ID
    public FilmResponseDTO getFilmById(Long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));
        return getFilmResponseDTO(film, film.getPricing(), film.getSeat());
    }

    // Update Film by ID
    public FilmResponseDTO updateFilm(Long filmId, FilmRequestDTO request) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found with id: " + filmId));

        film.setFilmTitle(request.getFilmTitle());
        film.setFilmDate(request.getFilmDate());
        film.setFilmTime(request.getFilmTime());
        film.setImageUrl(
                request.getImageUrl() != null ? request.getImageUrl() : film.getImageUrl()
        );

        Pricing pricing = film.getPricing();
        pricing.setLevel1Price(request.getL1Price());
        pricing.setLevel2Price(request.getL2Price());
        pricing.setBoxPrice(request.getBoxPrice());

        filmRepository.save(film);

        log.info("Updated film with id: {}", filmId);

        return getFilmResponseDTO(film, pricing, film.getSeat());
    }

    // Delete Film by ID
    public void deleteFilm(Long filmId) {
        if (!filmRepository.existsById(filmId)) {
            throw new RuntimeException("Film not found with id: " + filmId);
        }
        filmRepository.deleteById(filmId);
        log.info("Deleted film with id: {}", filmId);
    }


    // Helper method to convert Film, Pricing, and Seat to FilmResponseDTO
    private static FilmResponseDTO getFilmResponseDTO(Film newFilm, Pricing pricing, Seat seat) {
        FilmResponseDTO response = new FilmResponseDTO();
        response.setId(newFilm.getId());
        response.setFilmTitle(newFilm.getFilmTitle());
        response.setFilmDate(newFilm.getFilmDate().toString());
        response.setFilmTime(newFilm.getFilmTime().toString());
        response.setImageUrl(newFilm.getImageUrl());
        response.setL1Price(pricing.getLevel1Price());
        response.setL2Price(pricing.getLevel2Price());
        response.setBoxPrice(pricing.getBoxPrice());
        response.setLevel1Seats(seat.getLevel1Seats());
        response.setLevel2Seats(seat.getLevel2Seats());
        response.setBoxSeats(seat.getBoxSeats());
        return response;
    }

}
