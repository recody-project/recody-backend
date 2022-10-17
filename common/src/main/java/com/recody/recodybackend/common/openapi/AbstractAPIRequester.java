package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.common.openapi.annotation.APIProviderAnnotationResolver;
import com.recody.recodybackend.common.openapi.annotation.AuthenticateWith;
import com.recody.recodybackend.common.openapi.annotation.DefaultAPIProviderAnnotationResolver;
import org.slf4j.Logger;
import org.springframework.http.RequestEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;


/*
 * 웹 요청을 수행하고 응답을 반환하는 APIRequester 타입과 호환되는 base class
 * APIRequester<T extends APIRequest> 와 같은 형태를 띄고 있다.
 * 하위 클래스에서 이 클래스와
 * T : APIRequester 클래스에서 요구하는 APIRequest 의 서브타입이다.
 *   하위 구현체들이 다양한 API path 로 요청할 수 있도록 다양한 타입으로 구현될 수 있다.
 *   따라서 스프링을 쓰는 경우, 다양한 제네릭 인터페이스의 구현체들을 빈으로 등록할 수 있으므로 역할을 분산시킨다.
 * */
public abstract class AbstractAPIRequester<T extends APIRequest> implements APIRequester<T> {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationStrategy strategy;
    private final Logger log = getLogger(getClass());
    private final APIProviderAnnotationResolver providerResolver = new DefaultAPIProviderAnnotationResolver();
    
    
    /**
     * API Requester 는 인증 정보를 가진다.
     * 어노테이션에 명시된 인증 방식을 우선시한다.
     */
    @Deprecated
    protected AbstractAPIRequester(AuthType authType, String... authArgs) {
        AuthType decidedAuthType = decideAuthenticationType(authType);
        this.strategy = resolveAuthenticationStrategy(decidedAuthType, authArgs);
        logThisImplementation();
    }
    
    /**
     * 어노테이션에 병시된 인증 방식을 사용한다.
     * 없을 시 예외를 던진다.
     */
    @Deprecated
    protected AbstractAPIRequester(String... authArgs) {
        AuthType decidedAuthType = decideAuthenticationType();
        this.strategy = resolveAuthenticationStrategy(decidedAuthType, authArgs);
        logThisImplementation();
    }
    
    /**
     * {@code @APIProvider} 를 가진 요청 객체를 직접 받아 인증 정보를 세팅한다. <br>
     * AuthType 도 해당 객체에 명시된 {@code @AuthenticateWith} 어노테이션을 우선적으로 따른다.<br>
     * <pre> 요청 객체 예시
     *     {@code
     *  @APIProvider
     *  @AuthenticateWith(type = AuthType.API_KEY_QUERY_PARAM)
     *  public class TMDBAPIRequest extends AbstractAPIRequest {
     *
     *      private static final String TMDB_BASE_URI = "https://api.themoviedb.org/3";
     *
     *      @APIKeyName
     *      private String apiParam = "api_key";
     *
     *      @APIKeyValue
     *      @Value("${movie.tmdb.api-key}")
     *      private String apikeyValue;
     *   }
     *   }
     *   </pre>
     * @param request {@code @APIProvider} 어노테이션을 가진 객체
     */
    protected AbstractAPIRequester(T request){
        this.strategy = resolveAuthenticationStrategyFromAPIProvider(request);
        String baseUrl = providerResolver.getBaseUrl();
        request.setBaseUrl(baseUrl);
        logThisImplementation();
    }
    
    @Override
    public String executeToString(T request) {
        return doExecute(request, String.class);
    }
    
    @Override
    @Deprecated
    public JsonNode executeToJsonNode(T request) {
        return doExecute(request, JsonNode.class);
    }
    
    @Override
    @Deprecated
    public JsonAPIResponse executeToJson(T request) {
        JsonNode body = doExecute(request, JsonNode.class);
        return new JsonAPIResponse(body);
    }
    
    public String requestToString(APIFeature apiFeature) {
        return doExecute(apiFeature, String.class);
    }
    
    @Deprecated
    public JsonNode requestToJsonNode(APIFeature apiFeature) {
        return doExecute(apiFeature, JsonNode.class);
    }
    
    @Override
    @Deprecated
    public JsonAPIResponse requestToJson(APIFeature apiFeature) {
        JsonNode body = doExecute(apiFeature, JsonNode.class);
        return new JsonAPIResponse(body);
    }
    
    @Override
    public <S> S requestAndGet(APIFeature apiFeature, Class<S> clazz) {
        return doExecute(apiFeature, clazz);
    }
    
    @Override
    public <S> S requestAndGet(APIRequest apiRequest, Class<S> clazz) {
        return doExecute2(apiRequest, clazz);
    }
    
    private <S> S doExecute2(APIRequest apiRequest, Class<S> clazz) {
        S body;
        log.debug("executing request: {}", apiRequest);
        try {
            strategy.authenticate(apiRequest);
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, clazz).getBody();
        } catch (RestClientException exception) {
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException(
                    "외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." + " message: " + exception.getMessage() + " request: " + apiRequest);
        }
        return body;
    }
    
    private AuthType decideAuthenticationType() {
        AuthenticateWith annotation;
        if (getClass().isAnnotationPresent(AuthenticateWith.class))
            annotation = getClass().getAnnotation(AuthenticateWith.class);
        else
            return AuthType.NO_AUTH;
        Assert.notNull(annotation, "APIRequester requires AuthType!");
        AuthType typeFromAnnotation = annotation.type();
        if (typeFromAnnotation != null)
            log.info("API Requester instantiating with AuthType of [ {} ]", typeFromAnnotation);
        return typeFromAnnotation;
    }
    
    private AuthType decideAuthenticationType(AuthType authType) {
        Optional<AuthenticateWith>  annotation;
        if (getClass().isAnnotationPresent(AuthenticateWith.class))
            annotation = Optional.of(getClass().getAnnotation(AuthenticateWith.class));
        else
            annotation = Optional.empty();
        AuthType typeFromAnnotation = annotation.map(AuthenticateWith::type).orElse(null);
        AuthType switchingAuthType = (typeFromAnnotation != null) ? typeFromAnnotation : authType;
        Assert.notNull(switchingAuthType, "APIRequester requires AuthType!");
        log.info("API Requester instantiating with AuthType of [ {} ]", switchingAuthType);
        return switchingAuthType;
    }
    
    private <C> C doExecute(T request, Class<C> clazz) {
        C body;
        log.debug("executing request: {}", request);
        try {
            strategy.authenticate(request);
            RequestEntity<Object> entity = request.toEntity();
            body = restTemplate.exchange(entity, clazz).getBody();
        } catch (RestClientException exception) {
            log.warn("exception: {}", exception.getMessage());
            String message = null;
            try {
                String substring = Objects.requireNonNull(exception.getMessage()).substring(7);
                JsonNode jsonNode = objectMapper.readTree(substring);
                log.error("jsonNode: {}", jsonNode);
                message = jsonNode.get("error").get("type").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
//            throw new RuntimeException(
//                    "외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." + " message: " + exception.getMessage() + " request: " + apiFeature);
            throw new InternalServerError(message);
        }
        return body;
    }
    
    private <C> C doExecute(APIFeature apiFeature, Class<C> clazz) {
        C body;
        try {
            APIRequest apiRequest = apiFeature.toAPIRequest();
            strategy.authenticate(apiRequest);
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, clazz).getBody();
        } catch (RestClientException exception) {
            log.warn("exception: {}", exception.getMessage());
            String message = null;
            try {
                String substring = Objects.requireNonNull(exception.getMessage()).substring(7);
                JsonNode jsonNode = objectMapper.readTree(substring);
                log.error("jsonNode: {}", jsonNode);
                message = jsonNode.get("error").get("type").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
//            throw new RuntimeException(
//                    "외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." + " message: " + exception.getMessage() + " request: " + apiFeature);
            throw new InternalServerError(message);
        }
        return body;
    }
    
    private void logThisImplementation() {
        String[] split = getClass().getGenericSuperclass().getTypeName().split("\\.");
        String s = split[split.length - 1];
        log.info("Instantiated APIRequester for: [ " + s.substring(0,
                                                                   s.length() - 1) + " ]" + " AuthType: [" + this.strategy.getClass() + "]");
    }
    
    private String nonNull(String[] array, int index) {
        String value;
        try {
            value = array[index];
            return value;
        } catch (NullPointerException exception) {
            throw new RuntimeException("인증 정보가 부족합니다.");
        }
    }
    
    private AuthenticationStrategy resolveAuthenticationStrategy(AuthType switchingAuthType, String[] authArgs) {
        String apiKey;
        String username;
        String password;
        String apiKeyParamName;
        if (switchingAuthType == AuthType.BASIC_AUTH) {
            username = nonNull(authArgs, 0);
            password = nonNull(authArgs, 1);
            return new AuthenticationStrategy.BasicAuth(username, password);
        } else if (switchingAuthType == AuthType.API_KEY_QUERY_PARAM) {
            apiKeyParamName = nonNull(authArgs, 0);
            apiKey = nonNull(authArgs, 1);
            return new AuthenticationStrategy.ApiKeyRequestParam(apiKeyParamName, apiKey);
        } else if (switchingAuthType == AuthType.BEARER_TOKEN) {
            apiKey = nonNull(authArgs, 0);
            return new AuthenticationStrategy.Bearer(apiKey);
        } else {
            return new AuthenticationStrategy.NoAuth();
        }
    }
    
    private AuthenticationStrategy resolveAuthenticationStrategyFromAPIProvider(Object request) {
        providerResolver.init(request);
        AuthType switchingAuthType = providerResolver.getAuthType();
        String apiKey;
        String apiKeyParamName;
        if (switchingAuthType == AuthType.API_KEY_QUERY_PARAM) {
            apiKeyParamName = providerResolver.getApiKeyName();
            apiKey = providerResolver.getApiKeyValue();
            return new AuthenticationStrategy.ApiKeyRequestParam(apiKeyParamName, apiKey);
        } else if (switchingAuthType == AuthType.BEARER_TOKEN) {
            apiKey = providerResolver.getBearerToken();
            return new AuthenticationStrategy.Bearer(apiKey);
        } else {
            return new AuthenticationStrategy.NoAuth();
        }
    }
}
