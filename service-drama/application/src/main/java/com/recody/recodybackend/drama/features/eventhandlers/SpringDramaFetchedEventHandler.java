package com.recody.recodybackend.drama.features.eventhandlers;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.drama.SearchingKeyword;
import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.data.drama.DramaMapper;
import com.recody.recodybackend.drama.data.drama.DramaRepository;
import com.recody.recodybackend.drama.data.search.DramaSearchingKeywordCountEntity;
import com.recody.recodybackend.drama.data.search.DramaSearchingKeywordCountRepository;
import com.recody.recodybackend.drama.features.event.DramaFetched;
import com.recody.recodybackend.drama.features.event.DramaQueried;
import com.recody.recodybackend.drama.features.synchronizedramaseachlanguages.SynchronizeDramasEachLanguagesHandler;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
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
    
    private final DramaSearchingKeywordCountRepository keywordRepository;
    
    private final SynchronizeDramasEachLanguagesHandler<Void> synchronizeDramasEachLanguagesHandler;
    
    /**
     * 드라마가 쿼리되었을 때 마다 검색 횟수를 확인한 후 데이터를 충원한다.
     * - 이미 드라마가 검색된 적이 있는 경우, 추가적인 액션을 취하지 않는다.
     * - 단, 같은 텍스트로 5번에 한 번씩은 다음 로직을 수행하도록 한다.
     */
    @Override
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(DramaQueried event) {
        log.debug( "handling event: {}", event );
        Optional<DramaSearchingKeywordCountEntity> optionalCount =
                keywordRepository.findFirstByText( event.getKeyword() );
        if ( optionalCount.isPresent() ) {
            log.trace( "optionalCount is present" );
            DramaSearchingKeywordCountEntity dramaSearchKeywordEntity = optionalCount.get();
            dramaSearchKeywordEntity.increment();
            int count = dramaSearchKeywordEntity.getCount() % 5;
            log.trace( "count: {}", count );
            if ( count != 0 ) {
                return;
            }
        }
        else {
            keywordRepository.save( DramaSearchingKeywordCountEntity.builder()
                                                                    .text( event.getKeyword() )
                                                                    .build() );
        }
        synchronizeDramasEachLanguagesHandler.handle( SearchingKeyword.of( event.getKeyword() ) );
        
    }
    
    @Override
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    @Transactional
    @EventListener
    public void on(DramaFetched event) {
        log.debug( "handling event: {}", event );
        
        // 매퍼를 사용해서 dto 를 entity 로 바꾼다.
        List<TMDBDrama> dramas = event.getDramas();
        
        for (TMDBDrama drama : dramas) {
            // tmdb id 로 이미 있는지 확인한다.
            Optional<DramaEntity> optionalDrama = dramaRepository.findByTmdbId( drama.getId() );
            // entity 로 데이터를 저장한다.
            if ( optionalDrama.isEmpty() ) {
                DramaEntity dramaEntity = dramaMapper.newEntity( drama, event.getLocale() );
                dramaRepository.save( dramaEntity );
            }
            else {
                dramaMapper.update( optionalDrama.get(), drama, event.getLocale() );
            }
        }
    }
}
