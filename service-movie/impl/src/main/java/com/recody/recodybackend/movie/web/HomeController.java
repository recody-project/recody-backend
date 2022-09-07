package com.recody.recodybackend.movie.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    
    @GetMapping("/")
    public String health(){
        return "hi this is recody";
    }
}
