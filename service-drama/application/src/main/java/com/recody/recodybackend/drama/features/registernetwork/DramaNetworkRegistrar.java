package com.recody.recodybackend.drama.features.registernetwork;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkInfoEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaNetworkRegistrar implements AsyncLinkingEntityManager<DramaNetworkEntity, DramaEntity, DramaNetworkInfoEntity> {
    
    private final DramaNetworkRepository networkRepository;
    
    @Override
    @Transactional
    public DramaNetworkEntity save(DramaEntity entity, DramaNetworkInfoEntity networkInfo) {
        Optional<DramaNetworkEntity> optionalNetwork =
                networkRepository.findByDramaAndNetworkInfo( entity, networkInfo );
        
        if ( optionalNetwork.isPresent() ) {
            DramaNetworkEntity dramaNetworkEntity = optionalNetwork.get();
            log.trace( "이미 존재하는 Network 를 반환: {}", dramaNetworkEntity );
            return dramaNetworkEntity;
        }
        
        DramaNetworkEntity networkEntity = DramaNetworkEntity.builder()
                                                     .drama( entity )
                                                     .networkInfo( networkInfo )
                                                     .build();
        DramaNetworkEntity savedEntity = networkRepository.save( networkEntity );
        log.trace( "saved network: {}", savedEntity );
        return savedEntity;
    }

    @Override
    @Async( Recody.DRAMA_TASK_EXECUTOR )
    public CompletableFuture<DramaNetworkEntity> saveAsync(DramaEntity entity,
                                                           DramaNetworkInfoEntity networkInfo) {
        return AsyncLinkingEntityManager.super.saveAsync( entity, networkInfo );
    }

    @Override
    @Async( Recody.DRAMA_TASK_EXECUTOR )
    public CompletableFuture<List<DramaNetworkEntity>> saveAsync(DramaEntity entity,
                                                                 List<DramaNetworkInfoEntity> dramaNetworkInfoEntities) {
        return AsyncLinkingEntityManager.super.saveAsync( entity, dramaNetworkInfoEntities );
    }

    @Override
    @Transactional
    public List<DramaNetworkEntity> save(DramaEntity entity,
                                         List<DramaNetworkInfoEntity> dramaNetworkInfoEntities) {
        return AsyncLinkingEntityManager.super.save( entity, dramaNetworkInfoEntities );
    }
}
