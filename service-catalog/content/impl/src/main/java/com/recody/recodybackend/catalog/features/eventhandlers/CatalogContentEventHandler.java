package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.movie.events.MovieCreated;

public interface CatalogContentEventHandler {
    
    void on(MovieCreated event);
    
}
