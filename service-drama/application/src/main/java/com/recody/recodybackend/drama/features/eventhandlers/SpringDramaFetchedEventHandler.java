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
import com.recody.recodybackend.drama.DramaId;
import com.recody.recodybackend.drama.features.synchronizedramadetail.SynchronizeDramaDetailHandler;
import com.recody.recodybackend.drama.features.synchronizedramaseachlanguages.SynchronizeDramasEachLanguagesHandler;
import com.recody.recodybackend.drama.PersonId;
import com.recody.recodybackend.drama.features.synchronizepersonname.SynchronizePersonNameHandler;
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
class SpringDramaFetchedEventHandler implements DramaFetchedEventHandler,
                                                EmptyDramaQueriedHandler,
                                                DramaDetailRequestedHandler,
                                                NoPersonNameFoundEventHandler{
    
    private final DramaMapper dramaMapper;
    
    private final DramaRepository dramaRepository;
    
    private final DramaSearchingKeywordCountRepository keywordRepository;
    
    private final SynchronizeDramasEachLanguagesHandler<Void> synchronizeDramasEachLanguagesHandler;
    
    private final SynchronizeDramaDetailHandler<Void> synchronizeDramaDetailHandler;
    
    private final SynchronizePersonNameHandler<Void> synchronizePersonNameHandler;
    
    @Override
    @Transactional
    @EventListener
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    public void on(NoPersonNameFound event) {
        log.trace( "handling event: {}", event );
        synchronizePersonNameHandler.handle( PersonId.of( event.getPersonId() ) );
    }
    
    @Override
    @Transactional
    @EventListener
    @Async( value = Recody.DRAMA_TASK_EXECUTOR )
    public void on(DramaDetailRequested event) {
        log.trace( "handling event: {}", event );
        //TODO: ????????? ????????? ??????
        synchronizeDramaDetailHandler.handle( DramaId.of( event.getDramaId() ) );
    }
    
    /**
     * ???????????? ??????????????? ??? ?????? ?????? ????????? ????????? ??? ???????????? ????????????.
     * - ?????? ???????????? ????????? ?????? ?????? ??????, ???????????? ????????? ????????? ?????????.
     * - ???, ?????? ???????????? 5?????? ??? ????????? ?????? ????????? ??????????????? ??????.
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
        
        // ????????? ???????????? dto ??? entity ??? ?????????.
        List<TMDBDrama> dramas = event.getDramas();
        
        for (TMDBDrama drama : dramas) {
            // tmdb id ??? ?????? ????????? ????????????.
            Optional<DramaEntity> optionalDrama = dramaRepository.findByTmdbId( drama.getId() );
            // entity ??? ???????????? ????????????.
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
