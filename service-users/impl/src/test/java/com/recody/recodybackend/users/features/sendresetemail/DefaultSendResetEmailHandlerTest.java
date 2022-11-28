package com.recody.recodybackend.users.features.sendresetemail;

import com.recody.recodybackend.users.RecodyUserApplication;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyUserApplication.class)
class DefaultSendResetEmailHandlerTest {
    
    public static final String PASSWORD = "pass";
    public static final String USER_EMAIL = "recodyarecody@outlook.com";
    @Autowired
    SendResetEmailHandler sendResetEmailHandler;
    
    @Autowired
    RecodyUserRepository userRepository;
    
    @BeforeEach
    void before() {
        RecodyUserEntity user = RecodyUserEntity.builder().email( USER_EMAIL ).socialType( SocialProvider.NONE ).password( PASSWORD ).role( Role.ROLE_MEMBER ).build();
        userRepository.save( user );
    }
    
    @Test
    @DisplayName( "sendEmail" )
    void sendEmail() {
        // given
        SendResetEmail sendResetEmail = new SendResetEmail( USER_EMAIL );
        // when
        String handle = sendResetEmailHandler.handle( sendResetEmail );
    
        // then
    }
    
    @AfterEach
    void after() {
        userRepository.deleteAllInBatch();
    }
    
}