package com.recody.recodybackend.users.features.signin.adminsignin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class DelegatingAdminRegistrar {
    
    private final AdminRegistrar adminRegistrar;
    
    @PostConstruct
    void register(){
        adminRegistrar.register();
    }
    
}
