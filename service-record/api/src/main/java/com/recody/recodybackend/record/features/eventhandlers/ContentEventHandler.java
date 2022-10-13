package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.ContentRated;

public interface ContentEventHandler {
    
    void on(String key, ContentCreated event);
    void on(String key, ContentRated event);
    
}
