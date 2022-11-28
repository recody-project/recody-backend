package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {
    
    private final JwtManager jwtManager;
    private final UserDetailsService userDetailsService;
    private final UsersAuthenticationEntryPoint authenticationEntryPoint;
    public static final String[] permittingEndpoints = {"/", "/index.html", "/test/**",
                                                         "/api/*/users/signup/**","/api/*/users/sign-in",
                                                         "/api/*/users/refresh-token", "/errors", "/actuator/**",
                                                        "/api/*/users/send-reset-email"};
    // TODO: actuator 엔드포인트 인증하게 하는 방법?
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);
        
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtManager, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(), JwtAuthenticationFilter.class)
                .addFilterBefore(characterEncodingFilter, CsrfFilter.class)
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
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(new UsersAccessDeniedHandler())
        ;
        return http.build();
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(permittingEndpoints);
    }
}
