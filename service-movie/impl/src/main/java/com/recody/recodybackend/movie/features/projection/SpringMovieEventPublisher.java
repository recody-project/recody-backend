package com.recody.recodybackend.movie.features.projection;

import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import com.recody.recodybackend.movie.events.MovieCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Conditional( value = OnSpringEventProcessingStrategy.class )
class SpringMovieEventPublisher implements MovieEventPublisher {
    
    private final ApplicationEventPublisher publisher;
    
    @Override
    public void publish(MovieCreated event) {
        publisher.publishEvent( event );
        log.info( "published event [Spring]: {}", event );
    }
}
