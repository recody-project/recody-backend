package com.recody.recodybackend.movie.config;

import com.recody.recodybackend.common.Recody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executor;

@Configuration
@Slf4j
@EnableAsync
public class ApplicationConfig {
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재 날짜 = {}", new Date());
        log.info("currentTimeMillis = {}", new Date(System.currentTimeMillis()));
    }
    
    /*
    * @Async 어노테이션 이 붙은 메서드
    * ThreadPoolTaskExecutor -> 생으로 쓰면 스프링이 관리하지 않는 쓰레드를 사용함.
    * 근데, 여기에 빈으로 등록해두고, @EnableAsync 요걸 해두면 스프링이 관리하는 쓰레드풀에서 쓰레드를 꺼내쓴다.
    * */
    @Bean( Recody.MOVIE_TASK_EXECUTOR )
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(30);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("movie-task-");
        executor.initialize();
        return executor;
    }
}
