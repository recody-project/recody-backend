package com.recody.recodybackend.drama.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.recody.recodybackend.drama.data")
@PropertySource(value = {"classpath:env.${spring.config.activate.on-profile}.properties"})
public class DramaDataApplication {

}
