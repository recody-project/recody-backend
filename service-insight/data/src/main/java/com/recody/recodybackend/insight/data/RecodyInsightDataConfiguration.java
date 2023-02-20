package com.recody.recodybackend.insight.data;

import com.recody.recodybackend.common.Recody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
class RecodyInsightDataConfiguration {
   
    
    @Bean(value = Recody.INSIGHT_TASK_EXECUTOR)
    Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("insight-task-");
        executor.initialize();
        return executor;
    }
    
}
