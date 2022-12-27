package com.recody.recodybackend.users.features.mail;

import com.recody.recodybackend.users.VerificationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnMissingBean( MailManager.class )
class EmptyMailManager implements MailManager {
    
    @Override
    public void sendPasswordResetEmail(VerificationCode verificationCode, String to) {
        throw new RuntimeException( "메일 보내기 기능을 지원하지 않습니다." );
    }
}
