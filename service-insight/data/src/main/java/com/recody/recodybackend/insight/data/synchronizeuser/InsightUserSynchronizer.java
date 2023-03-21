package com.recody.recodybackend.insight.data.synchronizeuser;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.insight.data.user.InsightUserEntity;
import com.recody.recodybackend.insight.data.user.InsightUserRepository;
import com.recody.recodybackend.users.FetchAllUsersResponse;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Lazy
class InsightUserSynchronizer {
    
    private final InsightUserRepository userRepository;
    
    private final WebClient webClient;
    
    public static final String path = "/api/v1/users/users";
    
    @Async( Recody.INSIGHT_TASK_EXECUTOR)
    void synchronize() {
        try {
            // 앱이 완전히 뜰때까지 기다린다.
            Thread.sleep( 10000L );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        log.info( " Insight User 동기화 시도 " );
        List<InsightUserEntity> users = userRepository.findAllByRole( Role.ROLE_MEMBER );
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
        if ( userList == null ) throw new InternalServerError();
        if ( userList.isEmpty() ) {
            log.warn( " 유저가 없으므로 동기화하지 않음. " );
            return;
        }
        List<InsightUserEntity> catalogUserEntities = newEntity( userList );
        
        List<InsightUserEntity> savedCatalogUserEntities = userRepository.saveAll( catalogUserEntities );
        log.info( " Insight 유저 {} 명 동기화 완료", savedCatalogUserEntities.size() );
    }
    
    private List<InsightUserEntity> newEntity(List<UserCreated> userList) {
        return userList.stream()
                       .map( user -> InsightUserEntity
                                             .builder()
                                             .id( user.getUserId() )
                                             .role( user.getRole() )
                                             .nickname( user.getNickname() )
                                             .build()
                           )
                       .collect( Collectors.toList() );
    }
}
