package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.ContentCreated;

public interface ContentEventHandler {
    
    void on(ContentCreated event);
    
}
