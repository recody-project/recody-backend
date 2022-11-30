package com.recody.recodybackend.users.features.resetpassword;

import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.data.*;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultResetPasswordHandler implements ResetPasswordHandler {
    
    private final RecodyUserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecodyUserMapper userMapper;
    
    @Override
    @Transactional
    public RecodyUserInfo handle(ResetPassword command) {
        String email = command.getEmail();
        
        RecodyUserEntity user = userRepository.findByEmail( email ).orElseThrow( UserNotFoundException::new );
        
        /* 인증 코드 확인 로직 시작 */
        // 없으면 잘못된 요청
//        String verificationCode = command.getVerificationCode().getValue();
//        VerificationCodeEntity verificationCodeEntity
//                = verificationCodeRepository.findFirstByUserOrderByCreatedAtDesc( user )
//                                            .orElseThrow( () -> ApplicationExceptions.badRequestOf(
//                                                    UsersErrorType.VerificationCodeNotFound ) );
//
//        // 인증코드를 발급한 유저 일치 확인
//        if ( !user.equals( verificationCodeEntity.getUser() ) ) {
//            throw ApplicationExceptions.badRequestOf( UsersErrorType.VerificationCodeNotMatch );
//        }
//        // 인증 코드 일치 여부 확인
//        if ( !verificationCode.equals( verificationCodeEntity.getVerificationCode() ) ) {
//            throw ApplicationExceptions.badRequestOf( UsersErrorType.VerificationCodeNotMatch );
//        }
        /* 인증 코드 확인 로직 끝 */
    
    
        String rawPassword = command.getPassword();
        String encodedPassword = passwordEncoder.encode( rawPassword );
        boolean matches = passwordEncoder.matches( command.getPasswordConfirm(), encodedPassword );
        if ( !matches ){
            throw ApplicationExceptions.badRequestOf( UsersErrorType.PasswordConfirmNotMatch );
        }
        user.setPassword( encodedPassword );
        log.info( "id {} 유저의 비밀번호를 업데이트하였습니다.", user.getUserId() );
        RecodyUserInfo info = userMapper.map( user );
        return info;
    }
}
