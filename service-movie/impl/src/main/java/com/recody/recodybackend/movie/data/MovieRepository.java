package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {
    
    Optional<MovieEntity> findByTmdbId(Integer tmdbId);
}
