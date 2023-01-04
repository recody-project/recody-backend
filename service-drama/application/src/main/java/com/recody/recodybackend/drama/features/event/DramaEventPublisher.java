package com.recody.recodybackend.drama.features.event;

public interface DramaEventPublisher {
    
    void publish(DramaFetched event);
    
    void publish(DramaQueried event);
    
}
