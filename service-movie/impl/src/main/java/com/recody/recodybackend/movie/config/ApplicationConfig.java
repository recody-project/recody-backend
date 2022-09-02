package com.recody.recodybackend.movie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:env.${spring.config.activate.on-profile}.properties"})
public class ApplicationConfig {
}
