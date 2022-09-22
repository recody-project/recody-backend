package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {
    
    private final JwtManager jwtManager;
    private final UserDetailsService userDetailsService;
    
    private static final String[] permittingEndpoints = {"/","/test/**", "/api/v1/login/**", "/api/v1/users/refresh-token",
                                                         "/**"};
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtManager, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(), JwtAuthenticationFilter.class)
                .cors()
            .and()
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(permittingEndpoints).permitAll()
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        return http.build();
    }
}
