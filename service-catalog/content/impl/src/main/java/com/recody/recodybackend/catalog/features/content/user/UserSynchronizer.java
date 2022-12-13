package com.recody.recodybackend.catalog.features.content.user;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserMapper;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.events.UserCreated;
import com.recody.recodybackend.users.FetchAllUsersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Lazy
class UserSynchronizer {
    
    private final CatalogUserRepository userRepository;
    
    private final CatalogUserMapper userMapper;
    private final WebClient webClient;
    
    public static final String path = "/api/v1/users/users";
    
    @Async( Recody.CATALOG_TASK_EXECUTOR )
    void synchronizeAsync() {
        try {
            // 앱이 완전히 뜰때까지 기다린다.
            Thread.sleep( 10000L );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        log.info( " Catalog User 동기화 시도 " );
        List<CatalogUserEntity> users = userRepository.findAllByRole( Role.ROLE_MEMBER );
        if ( !users.isEmpty() ) {
            log.info( "유저가 존재하므로 동기화하지 않습니다.: {} 명", users.size() );
            return;
        }
        Mono<List<UserCreated>> usersResponseMono
                = webClient.get()
                           .uri( uriBuilder -> uriBuilder.path( path )
                                                         .build() )
                           .retrieve()
                           .bodyToMono( FetchAllUsersResponse.class )
                           .map( FetchAllUsersResponse::getUsers );
        List<UserCreated> userList = usersResponseMono.block();
        if (userList == null) throw new InternalServerError();
        if ( userList.isEmpty() ) {
            log.warn( " 유저가 없으므로 동기화하지 않음. " );
            return;
        }
        List<CatalogUserEntity> catalogUserEntities = userMapper.newEntity( userList );
        
        List<CatalogUserEntity> savedCatalogUserEntities = userRepository.saveAll( catalogUserEntities );
        log.info( " Catalog 유저 {} 명 동기화 완료", savedCatalogUserEntities.size() );
    }
}
