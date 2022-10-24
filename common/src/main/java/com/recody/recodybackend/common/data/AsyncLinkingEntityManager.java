package com.recody.recodybackend.common.data;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncLinkingEntityManager<LINKING_ENTITY, ONE, OTHER> extends LinkingEntityManager<LINKING_ENTITY, ONE, OTHER> {
    
    @Async
    default CompletableFuture<LINKING_ENTITY> saveAsync(ONE one, OTHER other){
        return CompletableFuture.completedFuture(this.save(one, other));
    }
    
    @Async
    default CompletableFuture<List<LINKING_ENTITY>> saveAsync(ONE one, List<OTHER> others){
        return CompletableFuture.completedFuture(this.save(one, others));
    }
}
