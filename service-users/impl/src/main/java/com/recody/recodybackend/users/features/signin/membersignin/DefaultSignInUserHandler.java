package com.recody.recodybackend.users.features.signin.membersignin;

import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.users.RecodySignInSession;
import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.features.signin.membership.MembershipManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSignInUserHandler implements SignInUserHandler{
    
    private final PasswordEncoder passwordEncoder;
    private final RecodyUserRepository recodyUserRepository;
    private final RecodyUserMapper recodyUserMapper;
    private final MembershipManager membershipManager;
    
    
    @Override
    public RecodySignInSession handle(SignInUser command) {
        log.debug( "handling command: {}", command );
        String email = command.getEmail().getEmail();
        String password = command.getPassword();
        RecodyUserEntity recodyUserEntity = recodyUserRepository.findByEmail( email )
                                                                .orElseThrow( UserNotFoundException::new );
        
        boolean matches = passwordEncoder.matches( password, recodyUserEntity.getPassword() );
        
        if (!matches){
            throw ApplicationExceptions.badRequestOf( UsersErrorType.IncorrectPassword );
        }
    
        RecodyUserInfo userInfo = recodyUserMapper.map( recodyUserEntity );
        RecodySignInSession recodySignInSession = membershipManager.createSessionInfo( userInfo, command.getUserAgent() );
        log.info( "로그인에 성공하였습니다.: {}", recodySignInSession );
        return recodySignInSession;
    }
}
