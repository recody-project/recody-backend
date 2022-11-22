package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Conditional(value = OnSpringEventProcessingStrategy.class)
@RequiredArgsConstructor
@Slf4j
class SpringCatalogUserEventHandler implements CatalogUserEventHandler{

    private final CatalogUserRepository catalogUserRepository;

    @Override
    @EventListener
    public void on(UserCreated event) {
        log.debug( "consuming event: {}", event );
        CatalogUserEntity entity = CatalogUserEntity.builder()
                                                    .id( event.getUserId() )
                                                    .role( event.getRole() )
                                                    .email( event.getEmail() )
                                                    .build();
        catalogUserRepository.save( entity );
        log.info( "Catalog 서비스에 유저가 동기화되었습니다.: {}", entity );
    }
}
