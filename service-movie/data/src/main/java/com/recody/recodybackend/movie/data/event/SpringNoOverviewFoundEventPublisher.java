package com.recody.recodybackend.movie.data.event;

import com.recody.recodybackend.movie.events.NoEnglishOverviewFound;
import com.recody.recodybackend.movie.events.NoKoreanOverviewFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class SpringNoOverviewFoundEventPublisher implements NoEnglishOverviewFoundEventPublisher,
                                                     NoKoreanOverviewFoundEventPublisher{
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public void publish(NoKoreanOverviewFound event) {
        log.debug( "publishing event: {}", event );
        applicationEventPublisher.publishEvent( event );
    }
    
    @Override
    public void publish(NoEnglishOverviewFound event) {
        log.debug( "publishing event: {}", event );
        applicationEventPublisher.publishEvent( event );
    }
}
