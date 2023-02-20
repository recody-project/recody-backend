package com.recody.recodybackend.insight.data.synchronizeuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
class DelegatingSynchronizeInsightUserHandler implements SynchronizeInsightUserHandler {
    
    private final InsightUserSynchronizer insightUserSynchronizer;
    
    @Override
    @PostConstruct // 앱이 뜨고 한번 확인한다.
    public void synchronize() {
        insightUserSynchronizer.synchronize();
    }
}
