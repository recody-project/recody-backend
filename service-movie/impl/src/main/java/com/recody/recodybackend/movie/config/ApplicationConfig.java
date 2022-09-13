package com.recody.recodybackend.movie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Configuration
@Slf4j
public class ApplicationConfig {
    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재 날짜 = {}", new Date());
        log.info("currentTimeMillis = {}", new Date(System.currentTimeMillis()));
    }
}
