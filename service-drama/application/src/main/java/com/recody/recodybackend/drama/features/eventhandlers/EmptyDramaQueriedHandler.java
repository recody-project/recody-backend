package com.recody.recodybackend.drama.features.eventhandlers;

import com.recody.recodybackend.drama.features.event.DramaQueried;

public interface EmptyDramaQueriedHandler {
    
    void on(DramaQueried event);
    
}
