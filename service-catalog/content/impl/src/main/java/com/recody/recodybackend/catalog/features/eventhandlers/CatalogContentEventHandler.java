package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.movie.events.MovieCreated;

public interface CatalogContentEventHandler {
    
    /**
     * MovieCreated 이벤트는 Movie 서비스가 publisher 이다.
     * 따라서 이 핸들러에서 트랜잭션이 필요한 경우, 직접 트랜잭션을 생성하여야 한다.
     * (@TransactionalEventListener 사용 금지)
     */
    void on(MovieCreated event);
    
}
