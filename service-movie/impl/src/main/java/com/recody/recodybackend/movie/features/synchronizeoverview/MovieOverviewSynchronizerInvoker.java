package com.recody.recodybackend.movie.features.synchronizeoverview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class MovieOverviewSynchronizerInvoker {
    
    private final MovieOverviewSynchronizer movieOverviewSynchronizer;
    
    @PostConstruct
    void invoke(){
//        movieOverviewSynchronizer.synchronize();
    }
}
