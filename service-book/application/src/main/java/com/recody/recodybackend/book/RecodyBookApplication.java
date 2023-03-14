package com.recody.recodybackend.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.recody.recodybackend")
@PropertySource(value = {"classpath:env.local.properties"})
public class RecodyBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyBookApplication.class, args);
    }
}
