package com.recody.recodybackend.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.recody.recodybackend")
@PropertySource(value = {"classpath:env.${spring.config.activate.on-profile}.properties"})
public class RecodyCatalogApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyCatalogApplication.class, args);
    }
}