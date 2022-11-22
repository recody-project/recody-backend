package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.event.ContentCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(value = OnSpringEventProcessingStrategy.class )
@RequiredArgsConstructor
@Slf4j
class SpringContentEventPublisher implements ContentEventPublisher{
    
    private final ApplicationEventPublisher publisher;
    
    @Override
    public void publish(ContentCreated event) {
        publisher.publishEvent( event );
        log.info( "published event [Spring]: {}", event );
    }
    
    @Override
    public void publish(ContentRated event) {
        publisher.publishEvent( event );
        log.info( "published event [Spring]: {}", event );
    
    }
}
