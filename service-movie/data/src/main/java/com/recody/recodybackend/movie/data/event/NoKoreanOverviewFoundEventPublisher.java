package com.recody.recodybackend.movie.data.event;

import com.recody.recodybackend.movie.events.NoKoreanOverviewFound;

public interface NoKoreanOverviewFoundEventPublisher {
    
    void publish(NoKoreanOverviewFound event);
}
