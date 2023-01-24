package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaGenreRepository extends JpaRepository<DramaGenreEntity, Long> {
    
    Optional<DramaGenreEntity> findByDramaAndGenreCode(DramaEntity drama, DramaGenreCodeEntity genreCode);
    
    
    
}
