package com.recody.recodybackend.common.data;

import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface LocalizedAsyncEntityRegistrar<ENTITY, SOURCE>{
    
    @Transactional
    ENTITY register(SOURCE source, Locale locale);
    
    @Transactional
    default List<ENTITY> register(List<SOURCE> sources, Locale locale){
        return sources.stream()
                      .map(it -> this.register( it, locale ))
                      .collect( Collectors.toList() );
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<ENTITY> registerAsync(SOURCE source, Locale locale){
        return CompletableFuture.completedFuture(this.register(source, locale));
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<List<ENTITY>> registerAsync(List<SOURCE> sources, Locale locale) {
        return CompletableFuture.completedFuture(this.register(sources, locale));
    }
    
}
