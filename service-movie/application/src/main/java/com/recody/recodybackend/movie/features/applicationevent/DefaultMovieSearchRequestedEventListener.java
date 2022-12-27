package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.data.search.MovieSearchingKeywordCountEntity;
import com.recody.recodybackend.movie.data.search.MovieSearchingKeywordCountRepository;
import com.recody.recodybackend.movie.features.synchronizemoviesearchresults.SynchronizeMovieSearchResultsHandler;
import com.recody.recodybackend.movie.search.MovieSearchKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieSearchRequestedEventListener {
    
    private final MovieSearchingKeywordCountRepository keywordCountRepository;
    
    private final SynchronizeMovieSearchResultsHandler<Void> synchronizeMovieSearchResultsHandler;
    
    /**
     * 영화 검색 이벤트를 처리한다.
     * <li> 5번에 한번 검색 키워드에 대한 결과를 DB 에 저장한다. </li>
     */
    @EventListener
    @Transactional
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    public void on(MovieSearchRequested event) {
        log.debug( "consuming event: {}", event );
        String keyword = event.getKeyword();
        Optional<MovieSearchingKeywordCountEntity> optionalCount =
                keywordCountRepository.findByKeyword( keyword );
        
        if ( optionalCount.isEmpty() ) {
            keywordCountRepository.save( new MovieSearchingKeywordCountEntity( keyword ) );
        }
        else {
            MovieSearchingKeywordCountEntity keywordCount = optionalCount.get();
            keywordCount.increment();
            int countRemainder = keywordCount.getCount() % 5;
            if ( countRemainder != 0 ) {
                return;
            }
        }
        synchronizeMovieSearchResultsHandler.handle( MovieSearchKeyword.of(keyword) );
    }
}
