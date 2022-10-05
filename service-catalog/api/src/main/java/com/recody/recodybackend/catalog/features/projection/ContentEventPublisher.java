package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.events.ContentCreated;

public interface ContentEventPublisher {
    
    void publish(ContentCreated event);
    
}
