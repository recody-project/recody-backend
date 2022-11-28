package com.recody.recodybackend.users.features.mail;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.users.VerificationCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMailManager implements MailManager{
    
    public static final String FROM = "recodyarecody@outlook.com";
    public static final String MAIL_SUBJECT = "RECODY 비밀번호 인증 코드입니다.";
    public static final String TEMPLATE_PATH = "password-reset";
    public static final String VERIFICATION_CODE_NAME = "verificationCode";
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Override
    @Async(value = Recody.USERS_TASK_EXECUTOR ) // TODO: reactive 하게 리펙토링 (이메일 보내면 쓰레드를 오래 blocking 함.)
    public void sendPasswordResetEmail(VerificationCode verificationCode, String to) {
        log.debug( "비밀번호 재설정을 위한 인증코드를 메일로 보냅니다. verificationCode: {}", verificationCode );
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable( VERIFICATION_CODE_NAME, verificationCode.getValue() );
        try {
            MimeMessageHelper message = new MimeMessageHelper( mimeMessage, false, "utf-8" );
            message.setFrom( FROM );
            message.setTo( to );
            message.setSubject( MAIL_SUBJECT );
            String processedMessage = templateEngine.process( TEMPLATE_PATH, context );
            message.setText( processedMessage, true );
            mailSender.send( message.getMimeMessage() );
        } catch ( MessagingException e ) {
            log.error( "인증 코드 이메일 보내기 실패: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
