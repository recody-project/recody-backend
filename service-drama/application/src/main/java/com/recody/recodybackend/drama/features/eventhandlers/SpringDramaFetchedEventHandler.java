package com.recody.recodybackend.drama.features.eventhandlers;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.features.event.DramaEventPublisher;
import com.recody.recodybackend.drama.features.event.DramaFetched;
import com.recody.recodybackend.drama.features.event.EmptyDramaQueried;
import com.recody.recodybackend.drama.features.searchdramafromtmdb.SearchDramaFromTMDB;
import com.recody.recodybackend.drama.features.searchdramafromtmdb.SearchDramaFromTMDBHandler;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import com.recody.recodybackend.drama.tmdb.TMDBSearchDramaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class SpringDramaFetchedEventHandler implements DramaFetchedEventHandler, EmptyDramaQueriedHandler {
    
    private final DramaMapper dramaMapper;
    
    private final DramaRepository dramaRepository;
    
    private final SearchDramaFromTMDBHandler<TMDBSearchDramaResponse> searchDramaFromTMDBHandler;
    
    private final DramaEventPublisher dramaEventPublisher;
    
    @Override
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(EmptyDramaQueried event) {
        log.debug( "handling event: {}", event );
        TMDBSearchDramaResponse response = searchDramaFromTMDBHandler.handle(
                SearchDramaFromTMDB.builder()
                                   .queryText( event.getKeyword() )
                                   .language( event.getLocale().getLanguage() )
                                   .build() );
        dramaEventPublisher.publish( DramaFetched.builder()
                                                 .dramas( response.getResults() )
                                                 .locale( event.getLocale() )
                                                 .build() );
    }
    
    @Override
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(DramaFetched event) {
        log.debug( "handling event: {}", event );
        
        // 매퍼를 사용해서 dto 를 entity 로 바꾼다.
        List<TMDBDrama> dramas = event.getDramas();
    
        for (TMDBDrama drama : dramas) {
            // tmdb id 로 이미 있는지 확인한다.
            Optional<DramaEntity> optionalDrama = dramaRepository.findByTmdbId( drama.getId() );
            // entity 로 데이터를 저장한다.
            if (optionalDrama.isEmpty()) {
                DramaEntity dramaEntity = dramaMapper.newEntity( drama, event.getLocale() );
                dramaRepository.save( dramaEntity );
            }
            else {
                dramaMapper.update( optionalDrama.get(), drama, event.getLocale() );
            }
        }
    }
}
