package com.recody.recodybackend.users.features.signin.googlelogin;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.utils.MappingUtils;
import com.recody.recodybackend.users.exceptions.ResourceAccessTokenExpiredException;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;
import com.recody.recodybackend.users.SocialProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    
    @Override
    public GoogleOAuthUserInfo getUserInfo(ResourceAccessToken token) throws ResourceAccessTokenExpiredException {
        log.debug("handling: {}", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token.getValue());
        String uri = createUri(googleResourceServerUrl);
        
        RequestEntity<Void> request = RequestEntity.get(uri).headers(headers).build();
        GoogleOAuthUserInfo body;
        try {
            body = restTemplate.exchange(request, GoogleOAuthUserInfo.class).getBody();
            System.out.println(body);
        } catch (RestClientException exception) {
            log.debug("exception: {}", exception.toString());
            // TODO: 응답에 따른 예외 처리
            throw new ResourceAccessTokenExpiredException();
        }
        log.debug("recieved attribute");
        return body;
    }
    
    
    @Override
    public ResourceAccessToken refreshResourceAccessToken(ResourceRefreshToken refreshToken) {
        if (refreshToken.getSocialProvider() != SocialProvider.GOOGLE) {
            throw new RuntimeException("잘못된 명령입니다.");
        }
        String uri = createUri(googleRefreshUrl);
        
        MultiValueMap<String, String>
                bodyMap = createRequestBodyMap(refreshToken);
        
        RequestEntity<MultiValueMap<String, String>>
                requestEntity = createRefreshTokensRequestEntity(uri, bodyMap);
        
        RefreshGoogleAccessTokenResponse response;
        log.debug("구글 Access Token 갱신 시도: requestEntity: {}", requestEntity);
        response = restTemplate.exchange(requestEntity, RefreshGoogleAccessTokenResponse.class).getBody();
        if (response == null || response.getAccessToken() == null) {
            log.warn("요청은 성공했으나 구글 서버에서 엑세스 토큰을 받지 못했습니다.");
            throw new ApplicationException(UsersErrorType.CannotRefreshResourceAccessToken, HttpStatus.NOT_FOUND);
        }
        log.info("Google accessToken 갱신 결과 : {}", response);
        
        return ResourceAccessToken.googleOf(response.getAccessToken());
    }
    
    
    private String createUri(String url) {
        return UriComponentsBuilder.fromUriString(url).encode().build().toUriString();
    }
    
    
    private RequestEntity<MultiValueMap<String, String>> createRefreshTokensRequestEntity(String uri, MultiValueMap<String, String> bodyMap) {
        return RequestEntity
                .method(HttpMethod.POST, uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(bodyMap);
    }
    
    
    private MultiValueMap<String, String> createRequestBodyMap(ResourceRefreshToken refreshToken) {
        MultiValueMap<String, String> bodyMap;
        GoogleRefreshTokenRequestBody body = GoogleRefreshTokenRequestBody
                .builder()
                .client_id(googleClientId)
                .refresh_token(refreshToken.getValue())
                .build();
        bodyMap = MappingUtils.toMultiValueMap(body);
        return bodyMap;
    }
}
