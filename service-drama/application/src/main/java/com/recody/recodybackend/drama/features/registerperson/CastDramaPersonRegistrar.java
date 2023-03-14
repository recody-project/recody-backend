package com.recody.recodybackend.drama.features.registerperson;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.data.LocalizedAsyncEntityRegistrar;
import com.recody.recodybackend.drama.data.people.DramaPersonEntity;
import com.recody.recodybackend.drama.data.people.DramaPersonMapper;
import com.recody.recodybackend.drama.data.people.DramaPersonRepository;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class CastDramaPersonRegistrar implements LocalizedAsyncEntityRegistrar<DramaPersonEntity, TMDBDramaCast>{
    
    private final DramaPersonMapper personMapper;
    
    private final DramaPersonRepository personRepository;
    
    @Override
    @Transactional
    public DramaPersonEntity register(TMDBDramaCast cast, Locale locale) {
        Integer tmdbDramaCastId = cast.getId();
        Optional<DramaPersonEntity> optionalPerson = personRepository.findByTmdbId( tmdbDramaCastId );
        if ( optionalPerson.isPresent() ) {
            DramaPersonEntity dramaPersonEntity = optionalPerson.get();
            log.trace( "foundEntity: {}", dramaPersonEntity );
            return dramaPersonEntity;
        }
        DramaPersonEntity dramaPersonEntity = personMapper.newEntity( cast, locale );
        DramaPersonEntity savedEntity = personRepository.save( dramaPersonEntity );
        log.trace( "savedEntity: {}", savedEntity );
        return savedEntity;
    }
    
    @Override
    @Transactional
    public List<DramaPersonEntity> register(List<TMDBDramaCast> casts, Locale locale) {
        return LocalizedAsyncEntityRegistrar.super.register( casts, locale );
    }
    
    @Override
    @Async( Recody.DRAMA_TASK_EXECUTOR )
    public CompletableFuture<DramaPersonEntity> registerAsync(TMDBDramaCast cast, Locale locale) {
        return LocalizedAsyncEntityRegistrar.super.registerAsync( cast, locale );
    }
    
    @Override
    @Async( Recody.DRAMA_TASK_EXECUTOR )
    public CompletableFuture<List<DramaPersonEntity>> registerAsync(List<TMDBDramaCast> casts,
                                                                    Locale locale) {
        return LocalizedAsyncEntityRegistrar.super.registerAsync( casts, locale );
    }
}
