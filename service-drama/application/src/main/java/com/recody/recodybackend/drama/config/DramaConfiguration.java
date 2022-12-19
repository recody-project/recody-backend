package com.recody.recodybackend.drama.config;

import com.recody.recodybackend.common.Recody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
class DramaConfiguration {
    
    @Bean( Recody.DRAMA_TASK_EXECUTOR )
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize( 10 );
        executor.setMaxPoolSize( 30 );
        executor.setQueueCapacity( 200 );
        executor.setThreadNamePrefix( "drama-task-" );
        executor.initialize();
        return executor;
    }
}
