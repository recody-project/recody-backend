package com.recody.recodybackend.users.config;

import com.recody.recodybackend.common.events.RecodyTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class RecodyUsersConfig {
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    NewTopic userTopic(){
        return TopicBuilder.name( RecodyTopics.USER ).build();
    }
}
