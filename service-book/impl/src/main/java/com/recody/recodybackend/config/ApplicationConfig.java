package com.recody.recodybackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executor;

@Slf4j
public class ApplicationConfig {
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재 날짜 = {}", new Date());
        log.info("currentTimeMillis = {}", new Date(System.currentTimeMillis()));
    }

//    @Bean
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(30);
//        executor.setQueueCapacity(200);
//        executor.setThreadNamePrefix("book-task-");
//        executor.initialize();
//        return executor;
//    }
}
