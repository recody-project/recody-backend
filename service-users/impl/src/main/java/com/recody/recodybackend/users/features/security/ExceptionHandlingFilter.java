package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.common.web.ErrorResponseBody;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
class ExceptionHandlingFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
                                                                                                                       ServletException,
                                                                                                                       IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature: {}", ex.getMessage());
            setErrorResponse(request, response, ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT: {}", ex.getMessage());
            setErrorResponse(request, response, ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT: {}", ex.getMessage());
            setErrorResponse(request, response, ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT: {}", ex.getMessage());
            setErrorResponse(request, response, ex);
        } catch (AccessDeniedException ex) {
            log.error("AccessDeniedException ex: {}", ex.getMessage());
            setErrorResponse(request, response, ex);
        }
    }
    
    @SneakyThrows
    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception){
        response.setStatus(403);
        response.setContentType("application/json");
        String body = ErrorResponseBody.forbiddenOf(exception, exception.getMessage(), request.getRequestURI()).asJson();
        try{
            response.getWriter().write(body);
        }catch (IOException e){
            // TODO: controller advice 로 가지 않음.
            throw new InternalServerError(e.getMessage());
        }
    }
}
