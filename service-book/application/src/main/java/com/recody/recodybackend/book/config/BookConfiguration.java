package com.recody.recodybackend.book.config;

import com.recody.recodybackend.common.Recody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executor;

@Slf4j
public class BookConfiguration {
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재 날짜 = {}", new Date());
        log.info("currentTimeMillis = {}", new Date(System.currentTimeMillis()));
    }

    @Bean( Recody.BOOK_TASK_EXECUTOR )
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
