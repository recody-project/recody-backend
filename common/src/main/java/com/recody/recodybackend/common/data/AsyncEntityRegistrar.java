package com.recody.recodybackend.common.data;

import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 객체를 엔티티로 저장하거나 가져오는 로직을 가지는 인터페이스.
 * @param <ENTITY> 반환하는 엔티티
 * @param <SOURCE> 엔티티를 등록 또는 찾기 위한 데이터
 * @author motive
 */
public interface AsyncEntityRegistrar<ENTITY, SOURCE> {
    
    @Transactional
    ENTITY register(SOURCE source);
    
    @Transactional
    default List<ENTITY> register(List<SOURCE> sources){
        return sources.stream()
                       .map(this::register)
                       .collect(Collectors.toList());
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<ENTITY> registerAsync(SOURCE source){
        return CompletableFuture.completedFuture(this.register(source));
    }
    
    @Async
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default CompletableFuture<List<ENTITY>> registerAsync(List<SOURCE> sources) {
        return CompletableFuture.completedFuture(this.register(sources));
    }
}
