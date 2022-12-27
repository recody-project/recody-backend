package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.features.synchronizemoviedetail.SynchronizeMovieDetail;
import com.recody.recodybackend.movie.features.synchronizemoviedetail.SynchronizeMovieDetailHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieDetailRequestedEventListener {
    
    private final SynchronizeMovieDetailHandler<Void> synchronizeMovieDetailHandler;
    
    @EventListener
    @Transactional
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    public void on(MovieDetailRequested event) {
        // movieId 를 사용해서 영화의 상세정보를 확인한다.
        log.debug( "handling event: {}", event );
        // TODO: 이벤트 핸들링 조절 로직
        
        
        synchronizeMovieDetailHandler.handle( SynchronizeMovieDetail.builder().tmdbId( event.getTmdbId() ).build() );
        
    }
    
}
