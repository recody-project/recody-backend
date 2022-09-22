package com.recody.recodybackend.record;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/*
* test 환경에서 Movie 모듈만으로도 스프링 애플리케이션으로 빌드될 수 있어야 한다.
* 필요한 환경변수들도 불러올 수 있도록 설정한다. */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.recody.recodybackend")
@PropertySource(value = {"classpath:env.test.properties"})
public class RecodyRecordApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyRecordApplication.class, args);
    }
}