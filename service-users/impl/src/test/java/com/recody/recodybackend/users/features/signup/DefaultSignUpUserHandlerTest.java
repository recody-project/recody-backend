package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.users.RecodyUserApplication;
import com.recody.recodybackend.users.RecodyUserEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles( "test" )
@ContextConfiguration( classes = RecodyUserApplication.class )
class DefaultSignUpUserHandlerTest {
    
    @Autowired
    SignUpUserHandler signUpUserHandler;
    
    @Test
    @DisplayName( "비밀번호 확인이 다르면 회원가입되지 않는다." )
    void signupTest() {
        // given
        String username = "user1";
        String password = "pass";
        String passwordConfirm = "passssss";
        String email = "email";
        SignUpUser command = newSignUpUserCommand( password, passwordConfirm, email );
        // when
        
        // then
        assertThatThrownBy( () -> signUpUserHandler.handle( command ) );
    }
    
    @ParameterizedTest
    @CsvSource( value = {"user1/pass/pass/", "/pass/pass/email", "user//pass/email", "user/pass//email"}, delimiter = '/' )
    void nullCheckTest(String username, String password, String passwordConfirm, String email) {
        // given
        System.out.println( "username = " + username + " / isNull: " + (username == null) );
        System.out.println( "password = " + password + " / isNull: " + (password == null) );
        System.out.println( "passwordConfirm = " + passwordConfirm + " / isNull: " + (passwordConfirm == null) );
        System.out.println( "email = " + email + " / isNull: " + (email == null) );
        
        // then
        assertThatThrownBy( () -> newSignUpUserCommand( password, passwordConfirm, email ) );
    }
    
    private SignUpUser newSignUpUserCommand(String password, String passwordConfirm, String email) {
        return SignUpUser.builder().email( RecodyUserEmail.of(  email ) ).password( password ).passwordConfirm( passwordConfirm ).build();
    }
    
    
}