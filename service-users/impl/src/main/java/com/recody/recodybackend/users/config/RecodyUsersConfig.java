package com.recody.recodybackend.users.config;

import com.recody.recodybackend.common.Recody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.Executor;

@Configuration
class RecodyUsersConfig {
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean( Recody.USERS_TASK_EXECUTOR )
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize( 10 );
        executor.setMaxPoolSize( 30 );
        executor.setQueueCapacity( 200 );
        executor.setThreadNamePrefix( "users-task-" );
        executor.initialize();
        return executor;
    }
}
