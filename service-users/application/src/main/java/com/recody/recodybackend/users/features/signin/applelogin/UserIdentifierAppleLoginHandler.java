package com.recody.recodybackend.users.features.signin.applelogin;

import com.recody.recodybackend.users.RecodySignInSession;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import com.recody.recodybackend.users.features.signin.MembershipManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class UserIdentifierAppleLoginHandler implements AppleLoginHandler<AppleLogin> {
    
    private final RecodyUserRepository recodyUserRepository;
    private final RecodyUserMapper recodyUserMapper;
    private final MembershipManager membershipManager;
    
    @Override
    public RecodySignInSession handle(AppleLogin command) {
        Optional<RecodyUserEntity> socialUserIdentifier
                = recodyUserRepository.findByAppleUserIdentifier( command.getUserIdentifier() );
        if ( socialUserIdentifier.isPresent() ) {
            RecodyUserEntity recodyUserEntity = socialUserIdentifier.get();
            RecodySignInSession recodySignInSession = membershipManager.createSessionInfo(
                    recodyUserMapper.map( recodyUserEntity ), command.getUserAgent() );
            log.info( "로그인에 성공하였습니다.: {}", recodySignInSession );
            return recodySignInSession;
        }
        throw new UserNotFoundException();
    }
}
