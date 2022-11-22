package com.recody.recodybackend.common.contents.register;

import com.recody.recodybackend.common.contents.Content;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface AsyncContentRegistrar<T extends Content<?>, SOURCE> extends ContentRegistrar<T, SOURCE> {
    
    @Async
    @Transactional
    default CompletableFuture<T> registerAsync(SOURCE source, Locale locale) {
        return CompletableFuture.completedFuture(this.register(source, locale));
    }
    
    @Async
    @Transactional
    default CompletableFuture<List<T>> registerAsync(List<SOURCE> source, Locale locale){
        return CompletableFuture.completedFuture(this.register(source, locale));
    }
    
}
