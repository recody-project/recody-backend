package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.ContentRated;

public interface ContentEventPublisher {
    
    void publish(ContentCreated event);
    void publish(ContentRated event);
    
}
