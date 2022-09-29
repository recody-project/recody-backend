package com.recody.recodybackend.catalog.features.api.movie;

import com.recody.recodybackend.common.openapi.AbstractAPIRequester;
import com.recody.recodybackend.common.openapi.AuthType;
import com.recody.recodybackend.common.openapi.annotation.AuthenticateWith;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AuthenticateWith(type = AuthType.BEARER_TOKEN)
@Slf4j
class MovieAPIRequester extends AbstractAPIRequester<MovieAPIRequest> {
    
    protected MovieAPIRequester(@Value("${catalog.movie.access-token}") String accessToken) {
        super(accessToken);
        log.info("accessToken: {}", accessToken);
    }
}
