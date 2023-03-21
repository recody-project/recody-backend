package com.recody.recodybackend.insight.data.synchronizeuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile( {"local", "dev", "prod"} )
class DelegatingSynchronizeInsightUserHandler implements SynchronizeInsightUserHandler {
    
    private final InsightUserSynchronizer insightUserSynchronizer;
    
    @Override
    @PostConstruct // 앱이 뜨고 한번 확인한다.
    public void synchronize() {
        insightUserSynchronizer.synchronize();
    }
}
