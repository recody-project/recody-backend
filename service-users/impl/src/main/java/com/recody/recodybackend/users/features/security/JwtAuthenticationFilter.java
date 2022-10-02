package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtManager jwtManager;
    private final UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String token = resolveToken(request);
        Authentication authentication;
        if (token != null && jwtManager.validateToken(token)) {
            log.debug("request processing with valid token: {}", token);
            String username = jwtManager.resolveSubject(token);
    
            // UserDetails 객체 : SecurityUser
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
            // Authentication 객체: UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(username, "", userDetails.getAuthorities());
    
            // SecurityContextHolder 에 Authentication 을 넣는다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Authentication set to SecurityHolder with JWT");
        }
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
