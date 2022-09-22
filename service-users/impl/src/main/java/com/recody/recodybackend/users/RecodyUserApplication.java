package com.recody.recodybackend.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.recody.recodybackend")
@PropertySource(value = {"classpath:env.test.properties"})
public class RecodyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyUserApplication.class, args);
    }
}