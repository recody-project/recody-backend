package com.recody.recodybackend.movie.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, String> {
    Optional<MovieGenreEntity> findByTmdbGenreId(Integer tmdbGenreId);
}
