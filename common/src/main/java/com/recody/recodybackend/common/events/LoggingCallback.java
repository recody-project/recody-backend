package com.recody.recodybackend.common.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class LoggingCallback implements ListenableFutureCallback<Object> {
    
    @Override
    public void onSuccess(Object result) {
        log.info("event published: {}", result);
    }
    
    @Override
    public void onFailure(Throwable ex) {
        log.error("event publishing failed: {}", ex.getMessage());
    }
}
