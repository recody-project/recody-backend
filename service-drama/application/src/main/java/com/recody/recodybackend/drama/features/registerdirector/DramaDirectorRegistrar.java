package com.recody.recodybackend.drama.features.registerdirector;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.people.DramaDirectorEntity;
import com.recody.recodybackend.drama.data.people.DramaDirectorRepository;
import com.recody.recodybackend.drama.data.people.DramaPersonEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DramaDirectorRegistrar implements AsyncLinkingEntityManager<DramaDirectorEntity,
                                                                         DramaEntity,
                                                                         DramaPersonEntity> {
    private final DramaDirectorRepository directorRepository;
    
    @Override
    @Transactional
    public DramaDirectorEntity save(DramaEntity entity, DramaPersonEntity person) {
        Optional<DramaDirectorEntity> optionalDirector =
                directorRepository.findByDramaAndPerson( entity, person );
        
        if (optionalDirector.isPresent()){
            DramaDirectorEntity dramaDirectorEntity = optionalDirector.get();
            log.trace( "이미 존재하는 director 정보를 반환.: {}", dramaDirectorEntity );
            return dramaDirectorEntity;
        }
        
        DramaDirectorEntity directorEntity = DramaDirectorEntity.builder().drama( entity )
                                                       .person( person )
                                                       .build();
        DramaDirectorEntity savedDirector = directorRepository.save( directorEntity );
        log.trace( "savedDirector: {}", savedDirector );
        return savedDirector;
    }
}
