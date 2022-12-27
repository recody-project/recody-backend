package com.recody.recodybackend.users.features.security;

import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import com.recody.recodybackend.common.web.ErrorResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UsersAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private final MessageSource ms;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
    
        response.setStatus(401);
        response.setContentType("application/json");
        String message = ms.getMessage(GlobalErrorType.InsufficientAuthentication.name(), null, request.getLocale());
        String body = ErrorResponseBody.of(GlobalErrorType.InsufficientAuthentication,
                                           message,
                                           request.getRequestURI()).asJson();
        response.getWriter().write(body);
    }
}
