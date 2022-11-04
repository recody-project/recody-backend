package com.recody.recodybackend.users.features.signin.adminsignin;

import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.events.UserCreated;
import com.recody.recodybackend.users.features.projection.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Component
@Order( Ordered.LOWEST_PRECEDENCE )
@Profile({"local", "test"})
@Lazy /* 테스트 환경에서 어드민 유저를 등록하는 시점을 미루기 위해 이 빈을 나중에 등록합니다.
         Kafka 토픽이 생성되지 않은 시점에 어드민 유저가 등록되면 정상적으로 user 토픽에 이벤트를 발행할 수 없습니다. */
public class AdminRegistrar {
    
    private final RecodyUserRepository recodyUserRepository;
    
    private final UserEventPublisher userEventPublisher;
    
    @Value("${users.admin.username}")
    private String username;
    
    @Value("${users.admin.password}")
    private String password;
    
    @Value("${users.admin.email}")
    private String email;
    
    @PostConstruct
    public void register(){
        Optional<RecodyUserEntity> optionalUser = recodyUserRepository.findByEmail( email );
        if (optionalUser.isPresent()){
            log.info("이미 어드민 유저가 있음. optionalUser: {}", optionalUser);
            return;
        }
        RecodyUserEntity adminUser = RecodyUserEntity.builder().userId( 1L ).username( username ).password( password ).email( email ).socialType(
                SocialProvider.NONE ).role(Role.ROLE_ADMIN).build();
        RecodyUserEntity savedAdmin = recodyUserRepository.save( adminUser );
        UserCreated event = UserCreated.builder()
                                       .userId( savedAdmin.getUserId() )
                                       .role( savedAdmin.getRole() )
                                       .email( savedAdmin.getEmail() )
                                       .build();
        userEventPublisher.publish( event );
        log.info("어드민 유저 등록 성공 savedAdmin: {}", savedAdmin);
    }
}
