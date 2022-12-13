package com.recody.recodybackend.movie.data.event;

import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import com.recody.recodybackend.movie.events.NoKoreanPersonNameFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Conditional( OnSpringEventProcessingStrategy.class )
class SpringNoKoreanPersonNameFoundEventPublisher implements NoKoreanPersonNameFoundEventPublisher{
    
    private final ApplicationEventPublisher publisher;
    
    @Override
    public void publish(NoKoreanPersonNameFound event) {
        log.debug( "publishing event: {}", event );
        publisher.publishEvent( event );
    }
}
