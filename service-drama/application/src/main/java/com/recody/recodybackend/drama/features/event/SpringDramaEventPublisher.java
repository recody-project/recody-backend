package com.recody.recodybackend.drama.features.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class SpringDramaEventPublisher implements DramaEventPublisher{
    
    private final ApplicationEventPublisher publisher;
    
    @Override
    public void publish(DramaFetched event) {
        log.debug( "publishing event: {}", event );
        publisher.publishEvent( event );
    }
    
    @Override
    public void publish(EmptyDramaQueried event) {
        log.debug( "publishing event: {}", event );
        publisher.publishEvent( event );
    }
}
