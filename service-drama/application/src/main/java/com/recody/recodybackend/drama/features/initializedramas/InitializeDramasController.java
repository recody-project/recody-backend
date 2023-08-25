package com.recody.recodybackend.drama.features.initializedramas;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InitializeDramasController {

    private final InitializeDramasHandler<Void> initializeDramasHandler;

    @GetMapping("/api/v1/drama/initialize")
    public Void initialize(){
        return initializeDramasHandler.handle();
    }

}
