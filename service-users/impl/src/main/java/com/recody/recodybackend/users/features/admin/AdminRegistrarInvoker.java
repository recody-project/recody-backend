package com.recody.recodybackend.users.features.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"local", "dev"})
class AdminRegistrarInvoker {
    
    private final AdminRegistrar adminRegistrar;
    
    @PostConstruct
    void invoke(){
        adminRegistrar.register();
    }
    
}
