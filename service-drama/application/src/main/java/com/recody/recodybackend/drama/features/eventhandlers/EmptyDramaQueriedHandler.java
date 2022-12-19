package com.recody.recodybackend.drama.features.eventhandlers;

import com.recody.recodybackend.drama.features.event.EmptyDramaQueried;

public interface EmptyDramaQueriedHandler {
    
    void on(EmptyDramaQueried event);
    
}
