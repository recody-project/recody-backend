package com.recody.recodybackend.movie.features.publisher;

import com.recody.recodybackend.common.events.DomainEventPublisher;
import com.recody.recodybackend.movie.features.applicationevent.MovieSearchRequested;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieSearchRequestedPublisher implements DomainEventPublisher<MovieSearchRequested> {
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public void publish(MovieSearchRequested movieSearchRequested) {
        if ( !StringUtils.hasText( movieSearchRequested.getKeyword()) ) {
            log.debug( "빈 문자열이므로 이벤트를 발행하지 않음: {}", movieSearchRequested );
            return;
        }
        applicationEventPublisher.publishEvent( movieSearchRequested );
    }
}
