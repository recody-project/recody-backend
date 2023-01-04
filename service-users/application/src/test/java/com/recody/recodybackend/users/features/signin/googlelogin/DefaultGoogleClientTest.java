package com.recody.recodybackend.users.features.signin.googlelogin;

import com.recody.recodybackend.users.RecodyUserApplication;
import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = RecodyUserApplication.class)
class DefaultGoogleClientTest {
    
    @Autowired GoogleClient client;
    
    @Value("${test.google.refresh-token}")
    private String refreshToken;
    
    @Test
    @DisplayName("리프레시 토큰으로 새로운 액세스 토큰을 받아올 수 있다.")
    void test01() {
        // given
        ResourceAccessToken resourceAccessToken = client.refreshResourceAccessToken(ResourceRefreshToken.googleOf(refreshToken));

        // when
        
        // then
        OAuthUserInfo userInfo = null;
        try {
            userInfo = client.getUserInfo(resourceAccessToken);
        } catch (ResourceAccessTokenExpiredException e) {
            e.printStackTrace();
        }
    
        System.out.println(userInfo);
    }
    
}