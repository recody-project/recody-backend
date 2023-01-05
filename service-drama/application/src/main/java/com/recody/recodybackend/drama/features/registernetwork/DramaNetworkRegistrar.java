package com.recody.recodybackend.drama.features.registernetwork;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkInfoEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaNetworkRegistrar implements AsyncLinkingEntityManager<DramaNetworkEntity, DramaEntity, DramaNetworkInfoEntity> {
    
    private final DramaNetworkRepository networkRepository;
    
    @Override
    @Transactional
    public DramaNetworkEntity save(DramaEntity entity, DramaNetworkInfoEntity networkInfo) {
        DramaNetworkEntity networkEntity = DramaNetworkEntity.builder()
                                                     .drama( entity )
                                                     .networkInfo( networkInfo )
                                                     .build();
        DramaNetworkEntity savedEntity = networkRepository.save( networkEntity );
        log.trace( "saved network: {}", savedEntity );
        return savedEntity;
    }
}
