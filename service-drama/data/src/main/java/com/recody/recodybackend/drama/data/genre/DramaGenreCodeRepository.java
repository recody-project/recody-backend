package com.recody.recodybackend.drama.data.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaGenreCodeRepository extends JpaRepository<DramaGenreCodeEntity, String> {
    
    Optional<DramaGenreCodeEntity> findByTmdbGenreId(Integer tmdbGenreId);
    

}
