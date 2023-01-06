package com.recody.recodybackend.drama.data.network;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaNetworkRepository extends JpaRepository<DramaNetworkEntity, Long> {
    
    Optional<DramaNetworkEntity> findByDramaAndNetworkInfo(DramaEntity drama,
                                                           DramaNetworkInfoEntity networkInfo);
}
