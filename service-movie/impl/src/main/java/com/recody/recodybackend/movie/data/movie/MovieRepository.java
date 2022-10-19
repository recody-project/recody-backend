package com.recody.recodybackend.movie.data.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, String>, MovieQueryRepository{
    
    Optional<MovieEntity> findByTmdbId(Integer tmdbId);
    
}
