package com.recody.recodybackend.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = RecodyCatalogApplication.class)
public class RecodyCatalogApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyCatalogApplication.class, args);
    }
}