package com.recody.recodybackend.drama.features.savenetwork;

import com.recody.recodybackend.common.data.AsyncEntityRegistrar;
import com.recody.recodybackend.drama.data.network.DramaNetworkInfoEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkInfoRepository;
import com.recody.recodybackend.drama.data.network.DramaNetworkMapper;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaNetwork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaNetworkInfoRegistrar implements AsyncEntityRegistrar<DramaNetworkInfoEntity, TMDBDramaNetwork> {
    
    private final DramaNetworkInfoRepository networkInfoRepository;
    private final DramaNetworkMapper networkMapper;
    
    
    @Override
    @Transactional
    public DramaNetworkInfoEntity register(TMDBDramaNetwork network) {
        Optional<DramaNetworkInfoEntity> optionalEntity =
                networkInfoRepository.findByTmdbId( network.getId() );
        if ( optionalEntity.isPresent() ) {
            log.trace( "found networkInfo: {}", optionalEntity );
            return optionalEntity.get();
        }
        DramaNetworkInfoEntity networkInfoEntity = networkMapper.newEntity( network );
        DramaNetworkInfoEntity savedEntity = networkInfoRepository.save( networkInfoEntity );
        log.trace( "saved networkInfo: {}", savedEntity );
        return savedEntity;
    }
}
