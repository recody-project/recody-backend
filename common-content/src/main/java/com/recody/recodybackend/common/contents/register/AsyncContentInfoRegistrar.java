package com.recody.recodybackend.common.contents.register;

import com.recody.recodybackend.common.contents.Content;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface AsyncContentInfoRegistrar<C extends Content<?>, TARGET, SOURCE> {
    
    @Transactional
    TARGET register(C content, SOURCE source, Locale locale);
    
    @Transactional
    default List<TARGET> register(C content, List<SOURCE> sources, Locale locale){
        return sources.stream()
                      .map(source -> this.register(content, source, locale))
                      .collect(Collectors.toList());
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<List<TARGET>> registerAsync(C content, List<SOURCE> sources, Locale locale){
        return CompletableFuture.completedFuture(this.register(content, sources, locale));
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<TARGET> registerAsync(C content, SOURCE source, Locale locale){
        return CompletableFuture.completedFuture(this.register(content, source, locale));
    }
    
}
