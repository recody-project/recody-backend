package com.recody.recodybackend.common;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface CommandHandler<TYPE, COMMAND> {
    
    TYPE handle(COMMAND command);
    
    @Async
    default CompletableFuture<TYPE> handleAsync(COMMAND command) {
        return CompletableFuture.completedFuture(this.handle(command));
    }
}
