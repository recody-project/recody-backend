package com.recody.recodybackend.users.features.signin.applelogin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

public interface AppleClient {
    
    
    @Component
    @RequiredArgsConstructor
    @Slf4j
    class DefaultAppleClient implements AppleClient {
    
    }
}
