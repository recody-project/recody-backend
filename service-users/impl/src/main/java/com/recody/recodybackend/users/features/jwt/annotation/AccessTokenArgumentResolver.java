package com.recody.recodybackend.users.features.jwt.annotation;

import com.recody.recodybackend.users.exceptions.FailedToResolveAccessTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AccessTokenArgumentResolver implements HandlerMethodArgumentResolver {
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameter = parameter.hasParameterAnnotation(
                AccessToken.class);
        log.debug("checked parameter for @AccessToken: {} ", hasParameter);
        return hasParameter;
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        log.debug("resolving @AccessToken");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = resolveToken(request);
        log.debug("resolved @AccessToken: {}", accessToken);
        return accessToken;
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token;
        if (!StringUtils.hasText(bearerToken) && !bearerToken.startsWith("Bearer ")) {
            throw new FailedToResolveAccessTokenException();
        }
        try{
            token = bearerToken.substring("Bearer ".length());
        } catch (Exception exception) {
            log.debug("caught exception: {}, throwing FailedToResolveAccessTokenException", exception.toString());
            throw new FailedToResolveAccessTokenException();
        }
        return token;
    }
}
