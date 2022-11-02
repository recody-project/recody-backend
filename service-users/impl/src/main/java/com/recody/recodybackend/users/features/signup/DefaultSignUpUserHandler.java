package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.users.RecodyUserEmail;
import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.SocialProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSignUpUserHandler implements SignUpUserHandler {
    
    private final RecodyUserRepository recodyUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecodyUserMapper userMapper;
    
    @Override
    @Transactional
    public RecodyUserInfo handle(SignUpUser command) {
        String email = command.getEmail().getEmail();
        RecodyUserEntity foundUser = recodyUserRepository.getByEmail( email );
        if ( foundUser != null ) {
            throw new ApplicationException( UsersErrorType.UserAlreadyExists,
                                            HttpStatus.BAD_REQUEST,
                                            "이미 존재하는 유저입니다. 로그인을 시도하세요." );
        }
        
        String rawPassword = command.getPassword();
        String rawPasswordConfirm = command.getPasswordConfirm();
        String nickname = command.getNickname();
        String pictureUrl = command.getPictureUrl();
        String name = command.getName();
        
        final String encodedPassword = passwordEncoder.encode( rawPassword );
        
        boolean matches = passwordEncoder.matches( rawPasswordConfirm, encodedPassword );
        
        if ( !matches ) {
            throw ApplicationExceptions.badRequestOf( UsersErrorType.PasswordConfirmNotMatch );
        }
        
        RecodyUserEntity newUser = RecodyUserEntity
                                           .builder()
                                           .name( name )
                                           .email( email )
                                           .pictureUrl( pictureUrl )
                                           .role( Role.ROLE_MEMBER )
                                           .socialType( SocialProvider.NONE )
                                           .password( encodedPassword )
                                           .nickname( nickname )
                                           .build();
        
        RecodyUserEntity savedUser = recodyUserRepository.save( newUser );
        RecodyUserInfo userInfo = userMapper.map( savedUser );
        log.info( "회원가입 완료: {}", userInfo );
        return userInfo;
    }
    
    @Override
    @Transactional
    public boolean checkDuplicateEmail(RecodyUserEmail email) {
        log.debug( "checking if email duplicate: {}", email );
        String emailValue = email.getEmail();
        return recodyUserRepository.existsByEmail( emailValue );
    }
}
