package com.recody.recodybackend.users.features.admin;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.events.UserCreated;
import com.recody.recodybackend.users.features.projection.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Component
@Order( Ordered.LOWEST_PRECEDENCE )
@Profile({"local", "dev"})
public class AdminRegistrar {
    
    private final RecodyUserRepository recodyUserRepository;
    
    private final UserEventPublisher userEventPublisher;
    
    @Value("${users.admin.username}")
    private String username;
    
    @Value("${users.admin.password}")
    private String password;
    
    @Value("${users.admin.email}")
    private String email;
    
    @Async( Recody.USERS_TASK_EXECUTOR )
    public void register(){
        try {
            Thread.sleep( 5000L );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
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
