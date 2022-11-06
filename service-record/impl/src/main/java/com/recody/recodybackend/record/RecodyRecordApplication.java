package com.recody.recodybackend.record;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
@EnableJpaRepositories(basePackages = {"com.recody.recodybackend"})
@EntityScan(basePackages = "com.recody.recodybackend")
@ComponentScan(basePackages = "com.recody.recodybackend")
@PropertySource(value = {"classpath:env.${spring.config.activate.on-profile}.properties"})
public class RecodyRecordApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyRecordApplication.class, args);
    }
}