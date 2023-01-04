package com.recody.recodybackend.drama.data.drama;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaRepository extends JpaRepository<DramaEntity, String>, DramaQueryRepository {
    
    Optional<DramaEntity> findByTmdbId(Integer tmdbId);
    
}
