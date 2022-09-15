package com.recody.recodybackend.users.config;

import com.recody.recodybackend.users.features.jwt.AccessTokenArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
class RecodyUsersWebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessTokenArgumentResolver());
    }
}
