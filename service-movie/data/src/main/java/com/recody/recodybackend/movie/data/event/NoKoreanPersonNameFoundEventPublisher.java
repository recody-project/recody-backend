package com.recody.recodybackend.movie.data.event;

import com.recody.recodybackend.movie.events.NoKoreanPersonNameFound;

public interface NoKoreanPersonNameFoundEventPublisher {
    
    void publish(NoKoreanPersonNameFound event);
    
}
