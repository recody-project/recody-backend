package com.recody.recodybackend.drama.features.eventhandlers;

import com.recody.recodybackend.drama.features.event.DramaFetched;

public interface DramaFetchedEventHandler {
    
    void on(DramaFetched event);
    
}
