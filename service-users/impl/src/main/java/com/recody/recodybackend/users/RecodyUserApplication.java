package com.recody.recodybackend.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = RecodyUserApplication.class)
public class RecodyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyUserApplication.class, args);
    }
}