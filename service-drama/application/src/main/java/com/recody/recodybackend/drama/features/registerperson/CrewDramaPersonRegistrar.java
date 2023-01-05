package com.recody.recodybackend.drama.features.registerperson;

import com.recody.recodybackend.common.data.LocalizedAsyncEntityRegistrar;
import com.recody.recodybackend.drama.data.people.DramaPersonEntity;
import com.recody.recodybackend.drama.data.people.DramaPersonMapper;
import com.recody.recodybackend.drama.data.people.DramaPersonRepository;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCrew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class CrewDramaPersonRegistrar implements LocalizedAsyncEntityRegistrar<DramaPersonEntity, TMDBDramaCrew>{
    
    private final DramaPersonMapper personMapper;
    
    private final DramaPersonRepository personRepository;
    
    @Override
    @Transactional
    public DramaPersonEntity register(TMDBDramaCrew crew, Locale locale) {
        Integer tmdbDramaCastId = crew.getId();
        Optional<DramaPersonEntity> optionalPerson = personRepository.findByTmdbId( tmdbDramaCastId );
        if ( optionalPerson.isPresent() ) {
            DramaPersonEntity dramaPersonEntity = optionalPerson.get();
            log.trace( "foundEntity: {}", dramaPersonEntity );
            return dramaPersonEntity;
        }
        DramaPersonEntity dramaPersonEntity = personMapper.newEntity( crew, locale );
        DramaPersonEntity savedEntity = personRepository.save( dramaPersonEntity );
        log.trace( "savedEntity: {}", savedEntity );
        return savedEntity;
    }
}
