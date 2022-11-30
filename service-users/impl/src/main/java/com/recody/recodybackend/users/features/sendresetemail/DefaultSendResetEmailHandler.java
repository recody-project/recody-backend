package com.recody.recodybackend.users.features.sendresetemail;

import com.recody.recodybackend.users.VerificationCode;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.data.VerificationCodeEntity;
import com.recody.recodybackend.users.data.VerificationCodeRepository;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import com.recody.recodybackend.users.features.mail.MailManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSendResetEmailHandler implements SendResetEmailHandler {
    private final RecodyUserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final MailManager mailManager;
    
    @Override
    @Transactional
    public String handle(SendResetEmail command) {
        log.debug( "handling command: {}", command );
        String email = command.getEmail();
        RecodyUserEntity user = userRepository.findByEmail( email ).orElseThrow( UserNotFoundException::new );
        VerificationCode verificationCode = VerificationCode.generate();
        
        // 인증 코드를 저장한다.
        VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder().verificationCode( verificationCode.getValue() ).user( user ).build();
        VerificationCodeEntity savedEntity = verificationCodeRepository.save( verificationCodeEntity );
        
        // 이메일을 보낸다.
        mailManager.sendPasswordResetEmail( verificationCode, email );
        return savedEntity.getVerificationCode();
    }
}
