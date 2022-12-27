package com.recody.recodybackend.catalog.features.genre.synchronizegenres;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class SynchronizerInvoker {
    
    private final MovieGenreSynchronizer movieGenreSynchronizer;
    
    @PostConstruct
    public void invoke(){
        movieGenreSynchronizer.synchronize();
    }
    
}
