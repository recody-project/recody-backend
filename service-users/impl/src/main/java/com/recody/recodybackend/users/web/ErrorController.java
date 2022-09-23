package com.recody.recodybackend.users.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ErrorController {
    
    @GetMapping("/errors/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
}
