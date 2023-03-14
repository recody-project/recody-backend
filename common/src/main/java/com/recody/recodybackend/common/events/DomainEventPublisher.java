package com.recody.recodybackend.common.events;

public interface DomainEventPublisher<EVENT> {
    
    void publish(EVENT event);
    
}
