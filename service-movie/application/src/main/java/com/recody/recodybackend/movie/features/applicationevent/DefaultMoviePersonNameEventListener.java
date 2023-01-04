package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.events.NoKoreanPersonNameFound;
import com.recody.recodybackend.movie.features.updatepersonname.UpdateKoreanPersonName;
import com.recody.recodybackend.movie.features.updatepersonname.UpdatePersonNameHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMoviePersonNameEventListener {
    
    private final UpdatePersonNameHandler<Void> updatePersonNameHandler;
    
    @EventListener
    @Async(value = Recody.MOVIE_TASK_EXECUTOR )
    public void on(NoKoreanPersonNameFound event) {
        log.debug( "consuming event: {}", event );
        Long personNameId = event.getPersonNameId();
        updatePersonNameHandler.handle( new UpdateKoreanPersonName( personNameId ) );
    }
}
