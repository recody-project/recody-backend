package com.recody.recodybackend.users.features.login.googlelogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.exceptions.SocialAccessTokenExpiredException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.features.login.GetUserInfoFromResourceServer;
import com.recody.recodybackend.users.features.login.JacksonOAuthAttributes;
import com.recody.recodybackend.users.features.login.SocialProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGoogleClient implements GoogleClient {
    
    @Value("${users.oauth2.google.resource-url}")
    private String googleResourceServerUrl;
    
    @Value("${users.oauth2.google.refresh-url}")
    private String googleRefreshUrl;
    
    @Value("${users.oauth2.google.client-id}")
    private String googleClientId;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    
    @SneakyThrows
    public JacksonOAuthAttributes getUserInfo(GetUserInfoFromResourceServer command){
        log.debug("handling: {}", command);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + command.getResourceAccessToken());
        String uri = UriComponentsBuilder.fromUriString(googleResourceServerUrl).encode().build().toUriString();
    
        RequestEntity<Void> request = RequestEntity.get(uri).headers(headers).build();
        String body;
        JsonNode jsonNode;
        JacksonOAuthAttributes attributes;
        try {
            body = restTemplate.exchange(request, String.class).getBody();
            jsonNode = Objects.requireNonNull(objectMapper.readTree(body));
            attributes = JacksonOAuthAttributes.of(SocialProvider.GOOGLE).attributes(jsonNode).build();
            log.debug("Google attributes: {}", attributes);
    
        } catch (Exception exception) {
            log.debug("exception: {}", exception.toString());
            throw new SocialAccessTokenExpiredException();
        }
        
        log.debug("recieved attribute: {}", attributes);
        return attributes;
    }
    
    @Override
    public RefreshGoogleAccessTokenResponse handle(RefreshGoogleAccessToken command) {
        String uri = UriComponentsBuilder.fromUriString(googleRefreshUrl).encode().build().toUriString();
        GoogleRefreshTokenRequestBody body = GoogleRefreshTokenRequestBody
                .builder()
                .client_id(googleClientId)
                .refresh_token(command.getResourceRefreshToken())
                .build();
    
        // application/x-www-form-urlencoded.
        // MultiValueMap 으로 변환할 때 예외가 발생할 수 있다.
        RequestEntity<MultiValueMap<String, String>> requestEntity;
        try {
            requestEntity = RequestEntity
                    .method(HttpMethod.POST, uri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body.toMultiValueMap());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("메서드 바디 변환 실패");
        }
    
        // refresh access token
        RefreshGoogleAccessTokenResponse response;
    
        try{
            log.debug("구글 Access Token 갱신 시도: requestEntity: {}", requestEntity);
            response = restTemplate.exchange(requestEntity, RefreshGoogleAccessTokenResponse.class).getBody();
        } catch (Exception exception){
            throw new ApplicationException(UsersErrorType.CannotRefreshResourceAccessToken,
                                           HttpStatus.NOT_FOUND,
                                           exception.getMessage());
        }
        return response;
    }
}
