package com.recody.recodybackend.drama.features.initializedramagenres;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InitializeDramaGenresController {
    
    private final InitializeDramaGenresHandler<Void> initializeDramaGenresHandler;
    
    @GetMapping("/api/v1/drama/genre/initialize")
    public Void initialize(){
        return initializeDramaGenresHandler.handle();
    }
}
