package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.data.overview.MovieOverviewEntity;
import com.recody.recodybackend.movie.data.overview.MovieOverviewRepository;
import com.recody.recodybackend.movie.events.NoEnglishOverviewFound;
import com.recody.recodybackend.movie.features.updateoverview.UpdateEnglishOverview;
import com.recody.recodybackend.movie.features.updateoverview.UpdateOverviewHandler;
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
class DefaultMovieOverviewEventListener {
    
    private final UpdateOverviewHandler<Void> updateOverviewHandler;
    
    private final MovieOverviewRepository overviewRepository;
    
    @EventListener
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @Transactional
    public void on(NoEnglishOverviewFound event){
        log.debug( "consuming event: {}", event );
        Long overviewId = event.getOverviewId();
        Optional<MovieOverviewEntity> optionalMovieOverviewEntity = overviewRepository.findById( overviewId );
        if ( optionalMovieOverviewEntity.isEmpty() ) {
            log.error( "영어 Overview 를 업데이트하는 과정에서 Overview 데이터를 찾지 못했습니다. overviewId: {}", overviewId );
            return;
        }
        MovieOverviewEntity movieOverviewEntity = optionalMovieOverviewEntity.get();
        Integer tmdbMovieId = movieOverviewEntity.getMovie().getTmdbId();
        updateOverviewHandler.handle( new UpdateEnglishOverview( tmdbMovieId ) );
    }
}
