package com.recody.recodybackend.drama.features.registeractor;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.people.DramaActorEntity;
import com.recody.recodybackend.drama.data.people.DramaActorRepository;
import com.recody.recodybackend.drama.data.people.DramaPersonEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaActorRegistrar implements AsyncLinkingEntityManager<DramaActorEntity,
                                                                      DramaEntity,
                                                                      DramaPersonEntity> {
    private final DramaActorRepository actorRepository;
    
    @Override
    @Transactional
    public DramaActorEntity save(DramaEntity entity, DramaPersonEntity person) {
        DramaActorEntity actorEntity = DramaActorEntity.builder()
                                                 .drama( entity )
                                                 .person( person ).build();
        DramaActorEntity savedActor = actorRepository.save( actorEntity );
        log.trace( "savedActor: {}", savedActor );
        return savedActor;
    }
}
