package com.recody.recodybackend.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 테스트를 위해 메일을 보내는 빈을 설정합니다.
 * 현재 사용하지 않습니다.
 * @author motive
 */
//@Configuration
class UsersTestConfig {
    @Value( "${users.mail.host}" )
    private String host;
    @Value( "${users.mail.port}" )
    private Integer port;
    @Value( "${users.mail.username}" )
    private String username;
    @Value( "${users.mail.password}" )
    private String password;
    
    @Bean
    JavaMailSender javaMailSender() {
        Properties properties = new Properties();
        properties.setProperty( "mail.smtp.auth", "true" );
        properties.setProperty( "mail.smtp.starttls.enable", "true" );
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost( host );
        javaMailSender.setPort( port );
        javaMailSender.setUsername( username );
        javaMailSender.setPassword( password );
        javaMailSender.setJavaMailProperties( properties );
        return javaMailSender;
    }
}
