package com.recody.recodybackend.drama.features.registerdramadetail;

import com.recody.recodybackend.common.data.AsyncEntityRegistrar;
import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.data.network.DramaNetworkEntity;
import com.recody.recodybackend.drama.data.network.DramaNetworkInfoEntity;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaNetwork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultRegisterDramaDetailHandler implements RegisterDramaDetailHandler<DramaEntity> {
    
    private final DramaMapper dramaMapper;
    
    private final DramaRepository dramaRepository;
    
    private final AsyncLinkingEntityManager<DramaNetworkEntity, DramaEntity, DramaNetworkInfoEntity>
            networkRegistrar;
    
    private final AsyncEntityRegistrar<DramaNetworkInfoEntity, TMDBDramaNetwork> networkInfoRegistrar;
    
    
    @Override
    @Transactional
    public DramaEntity handle(RegisterDramaDetail command) {
        TMDBDramaDetail detail = command.getDetail();
        Integer tmdbId = detail.getId();
        Locale locale = command.getLocale();
        // 이 때의 Drama 는 보통 저장되어있습니다. (검색 이벤트를 통해 저장됨.)
        // 저장되어 있지 않은 경우, 새로운 Drama 를 저장한 후 Detail 를 반영합니다.
        Optional<DramaEntity> optionalDramaEntity = dramaRepository.findByTmdbId( tmdbId );
        DramaEntity dramaEntity;
        
        if (optionalDramaEntity.isEmpty()){
            DramaEntity newDramaEntity = dramaMapper.newEntity( detail, locale );
            dramaEntity = dramaRepository.save( newDramaEntity );
            log.trace( "saved new Drama: {}", newDramaEntity );
        }
        else {
            dramaEntity = optionalDramaEntity.get();
        }
        
        // Network 정보를 저장하거나 가져와 연관관계를 세팅함.
        List<TMDBDramaNetwork> networks = detail.getNetworks();
        networkInfoRegistrar.registerAsync( networks )
                            .thenApply( networkInfoEntities ->
                                                networkRegistrar.saveAsync( dramaEntity,
                                                                            networkInfoEntities ) );
        
        // 남은 detail 정보들 DramaEntity 에 업데이트.
        dramaMapper.updateDetail( dramaEntity, detail, locale );
        log.trace( "updated dramaEntity: {}", dramaEntity );
        return dramaEntity;
    }
}
