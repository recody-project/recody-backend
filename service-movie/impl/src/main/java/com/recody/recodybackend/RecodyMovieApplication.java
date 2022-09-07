package com.recody.recodybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

//@EnableAutoConfiguration
@ComponentScan(basePackageClasses = RecodyMovieApplication.class)
public class RecodyMovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecodyMovieApplication.class, args);
    }
}