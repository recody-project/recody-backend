package com.recody.recodybackend.movie.data.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;
import java.util.Optional;

public interface MovieGenreCodeRepository extends JpaRepository<MovieGenreCodeEntity, Long> {
    
    Optional<MovieGenreCodeEntity> findByTmdbGenreId(Integer tmdbGenreId);
    
}
