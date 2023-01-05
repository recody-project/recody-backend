package com.recody.recodybackend.drama.data.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaPersonRepository extends JpaRepository<DramaPersonEntity, Long> {
    
    Optional<DramaPersonEntity> findByTmdbId(Integer tmdbId);
    
}
