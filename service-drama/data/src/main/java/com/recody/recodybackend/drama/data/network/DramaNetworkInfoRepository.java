package com.recody.recodybackend.drama.data.network;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaNetworkInfoRepository extends JpaRepository<DramaNetworkInfoEntity, Long> {
    
    Optional<DramaNetworkInfoEntity> findByTmdbId(Integer tmdbId);
}
