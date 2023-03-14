package com.recody.recodybackend.drama.features.registerdramacredit;

import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import com.recody.recodybackend.common.data.LocalizedAsyncEntityRegistrar;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.data.people.*;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCast;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCreditResponse;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCrew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultRegisterDramaCreditHandler implements RegisterDramaCreditHandler<DramaEntity> {
    
    private final DramaRepository dramaRepository;
    
    private final LocalizedAsyncEntityRegistrar<DramaPersonEntity, TMDBDramaCast> castPersonRegistrar;
    private final LocalizedAsyncEntityRegistrar<DramaPersonEntity, TMDBDramaCrew> crewPersonRegistrar;
    
    private final AsyncLinkingEntityManager<DramaActorEntity, DramaEntity, DramaPersonEntity> actorRegistrar;
    private final AsyncLinkingEntityManager<DramaDirectorEntity, DramaEntity, DramaPersonEntity>
            directorRegistrar;
    
    @Override
    @Transactional
    public DramaEntity register(RegisterDramaCredit command) {
        log.trace( "handling command: {}", command );
        Integer tmdbDramaId = command.getTmdbDramaId();
        TMDBDramaCreditResponse response = command.getResponse();
        Locale locale = command.getLocale();
        
        DramaEntity dramaEntity = dramaRepository.findByTmdbId( tmdbDramaId )
                                                 .orElseThrow( ContentNotFoundException::new );
        
        castPersonRegistrar.registerAsync( response.getCast(), locale )
                           .thenAccept( dramaPersonEntities -> {
                               List<DramaActorEntity> saved = actorRegistrar.save( dramaEntity,
                                                                                  dramaPersonEntities );
                               log.debug( "saved actor entities: {}", saved.size() );
                           } );
        crewPersonRegistrar.registerAsync( response.getCrew(), locale )
                           .thenAccept( dramaPersonEntities -> {
                               List<DramaDirectorEntity> saved = directorRegistrar.save( dramaEntity,
                                                                                        dramaPersonEntities );
                               log.debug( "saved director entities: {}", saved.size() );
                           } );
        
        return dramaEntity;
    }
}
