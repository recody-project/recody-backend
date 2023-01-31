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
import java.util.Optional;

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
        // 있는지 확인
        Optional<DramaActorEntity> optionalActor = actorRepository.findByDramaAndPerson( entity, person );
        if ( optionalActor.isPresent() ) {
            DramaActorEntity dramaActorEntity = optionalActor.get();
            log.trace( "이미 존재하는 배우 정보를 반환. : {}", dramaActorEntity );
            return dramaActorEntity;
        }
        
        // 없으므로 새로 저장.
        DramaActorEntity actorEntity = DramaActorEntity.builder()
                                                       .drama( entity )
                                                       .person( person ).build();
        DramaActorEntity savedActor = actorRepository.save( actorEntity );
        log.trace( "savedActor: {}", savedActor );
        return savedActor;
    }
}
