package com.recody.recodybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:env.${spring.config.activate.on-profile}.properties"})
public class RecodyBackendApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RecodyBackendApplication.class, args);
    }
    
}
