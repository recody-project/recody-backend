package com.recody.recodybackend.movie.data.event;

import com.recody.recodybackend.movie.events.NoEnglishOverviewFound;

public interface NoEnglishOverviewFoundEventPublisher {
    
    void publish(NoEnglishOverviewFound event);
    
}
