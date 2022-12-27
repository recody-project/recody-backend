package com.recody.recodybackend.catalog.features.content.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class DelegatingSynchronizeUsersHandler implements SynchronizeUsersHandler{
    
    private final UserSynchronizer userSynchronizer;
    
    @Override
    @PostConstruct // 앱이 뜨고 한번 확인한다.
    public void synchronize() {
        userSynchronizer.synchronizeAsync();
    }
}
