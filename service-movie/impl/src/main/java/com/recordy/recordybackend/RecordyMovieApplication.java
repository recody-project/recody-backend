package com.recordy.recordybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = RecordyMovieApplication.class)
public class RecordyMovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordyMovieApplication.class, args);
    }
}