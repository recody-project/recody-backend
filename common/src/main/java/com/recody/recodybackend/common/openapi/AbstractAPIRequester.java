package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.slf4j.LoggerFactory.getLogger;


/*
* 웹 요청을 수행하고 응답을 반환하는 APIRequester 타입과 호환되는 base class
* APIRequester<T extends APIRequest> 와 같은 형태를 띄고 있다.
* 하위 클래스에서 이 클래스와
* T : APIRequester 클래스에서 요구하는 APIRequest 의 서브타입이다.
*   하위 구현체들이 다양한 API path 로 요청할 수 있도록 다양한 타입으로 구현될 수 있다.
*   따라서 스프링을 쓰는 경우, 다양한 제네릭 인터페이스의 구현체들을 빈으로 등록할 수 있으므로 역할을 분산시킨다.
* */
public abstract class AbstractAPIRequester<T extends APIRequest> implements APIRequester<T>{
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger log = getLogger(getClass());
    private final String apiKey;
    
    /*
    * constructor of this class that sets apiKey for API Request. */
    protected AbstractAPIRequester(String apiKey) {
        this.apiKey = apiKey;
        infoLogTypeParameterOfThisImplementation();
    }
    
    /*
    * constructor without api key. */
    protected AbstractAPIRequester(){
        this.apiKey = null;
        infoLogTypeParameterOfThisImplementation();
    }
    
    @Override
    public String executeToString(T request) {
        String body;
        log.debug("executing request: {}", request);
        request.setApiKey(apiKey);
        try{
            body = restTemplate.exchange(request.toEntity(), String.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return body;
    }
    
    @Override
    public JsonNode executeToJsonNode(T request) {
        JsonNode body;
        log.debug("executing request: {}", request);
        request.setApiKey(apiKey);
        try{
            body = restTemplate.exchange(request.toEntity(), JsonNode.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return body;
    }
    
    @Override
    public JsonAPIResponse executeToJson(T request) {
        JsonNode body;
        log.debug("executing request: {}", request);
        request.setApiKey(apiKey);
        try{
            RequestEntity<Object> entity = request.toEntity();
            body = restTemplate.exchange(entity, JsonNode.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return new JsonAPIResponse(body);
    }
    
    public String requestToString(Request request) {
        String body;
        
        APIRequest apiRequest = request.toAPIRequest();
        apiRequest.setApiKey(apiKey);
        try{
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, String.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return body;
    }
    
    @Override
    public JsonNode requestToJsonNode(Request request) {
        JsonNode body;
        
        APIRequest apiRequest = request.toAPIRequest();
        apiRequest.setApiKey(apiKey);
        try{
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, JsonNode.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return body;
    }
    
    @Override
    public JsonAPIResponse requestToJson(Request request) {
        JsonNode body;
        APIRequest apiRequest = request.toAPIRequest();
        apiRequest.setApiKey(apiKey);
        try{
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, JsonNode.class).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다." +
                                       " message: " +  exception.getMessage() +
                                       " request: " + request);
        }
        return new JsonAPIResponse(body);
    }
    
    @Override
    public <S> S requestAndGet(Request request, Class<S> clazz) {
        S body;
        APIRequest apiRequest = request.toAPIRequest();
        apiRequest.setApiKey(apiKey);
        try{
            RequestEntity<Object> entity = apiRequest.toEntity();
            body = restTemplate.exchange(entity, clazz).getBody();
        } catch (RestClientException exception){
            log.warn("exception: {}", exception.getMessage());
            throw new RuntimeException("외부 API 서버에서 정보를 받아오는 데에 실패하였습니다.");
        }
        return body;
    }
    
    private void infoLogTypeParameterOfThisImplementation() {
        String[] split = getClass().getGenericSuperclass()
                                   .getTypeName()
                                   .split("\\.");
        String s = split[split.length - 1];
        log.info("Instantiated APIRequester for: [ " + s.substring(0, s.length()-1)+ " ]");
    }
    
    private String getApiKey() { return apiKey; }
}
