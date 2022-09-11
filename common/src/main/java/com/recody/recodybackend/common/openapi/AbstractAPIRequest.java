package com.recody.recodybackend.common.openapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/*
* 목적에 따라 RequestEntity 를 반환할 수 있는 객체이다.
* 이 클래스가 인스턴스화 될 때 다음의 사항들이 바로 결정된다.
*   - http method
*   - (optional) api key 세팅을 위한 name 값
*   - (optional) language 세팅을 위한 name 값
*
*
* toEntity() 가 호출될 때 다음의 요소들이 세팅된다.
*   1. baseUri + path 가 세팅됨.
*   2. method, header, body 가 확정된다.
*   3. URI 객체 생성
*
* 이 클래스의 toEntity() 메서드가 호출되면 내용을 바꿀 수 없다.
* api requester 는 toEntity() 를 호출한다. */
@SuppressWarnings("unchecked")
public abstract class AbstractAPIRequest implements APIRequest{
    private final UriComponentsBuilder uriComponentsBuilder;
    private final HttpHeaders headers = new HttpHeaders();
    private final String API_KEY_PARAM_NAME;
    private final String LANGUAGE_PARAM_NAME;
    private final HttpMethod method;
    private final Object body;
    private final String uriBase;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final String path;
    private URI uri;
    private String language;
    private String apiKey;
    
    
    protected AbstractAPIRequest(String baseUri, String path, String API_KEY_PARAM_NAME, String LANGUAGE_PARAM_NAME, String language) {
        this.uriBase = baseUri;
        this.API_KEY_PARAM_NAME = API_KEY_PARAM_NAME;
        this.LANGUAGE_PARAM_NAME = LANGUAGE_PARAM_NAME;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(baseUri);
        this.path = path;
        setPath(path);
        setLanguage(language);
        this.method = method();
        this.body = body();
    }
    
    /*
     * api key 만 세팅하는 경우. */
    protected AbstractAPIRequest(String baseUri, String path, String API_KEY_PARAM_NAME) {
        this.uriBase = baseUri;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(baseUri);
        this.API_KEY_PARAM_NAME = API_KEY_PARAM_NAME;
        this.LANGUAGE_PARAM_NAME = null;
        this.method = method();
        this.body = body();
        this.path = path;
        setPath(path);
    
    }
    
    /*
     * api key, language 없이 요청하는 경우.
     * api key 를 세팅하는 경우 예외가 발생한다. */
    protected AbstractAPIRequest(String baseUri, String path) {
        this.uriBase = baseUri;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(baseUri);
        this.API_KEY_PARAM_NAME = null;
        this.LANGUAGE_PARAM_NAME = null;
        this.method = method();
        this.body = body();
        this.path = path;
        setPath(path);
    }
    
    public final String getLanguage() { return language; }
    
    public final void addHeader(String key, String value) {
        headers.add(key, value);
    }
    
    /*
     * B : 바디 타입
     * 이 메서드를 호출하면 모든 uri, method, body, header 가 세팅되어 반환된다.
     */
    public final <B> RequestEntity<B> toEntity() {
        URI uri = createUri();
        this.uri = uri;
        
        // validates all uri components
        validateApiComponents();
        return makeEntity(uri, headers);
    }
    
    public final void addRequestParam(String key, String value) {
        uriComponentsBuilder.queryParam(key, value);
    }
    
    /*
     * 원하는 body 를 넣을 수 있다.
     * 기본 값은 null 로, toEntity() 가 RequestEntity<Void> 를 반환하게 된다. */
    public Object body() {
        return null;
    }
    
    /*
     * api key 를 세팅하는 방식은 바뀔 수 있다.
     * 기본 값은 요청 파라미터에 넣는 방식이다. */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        addRequestParam(API_KEY_PARAM_NAME, apiKey);
    }
    
    
    /* ==================== Protected / Private Methods =====================*/
    
    protected final void setPath(String path) {
        this.uriComponentsBuilder.path(path);
    }
    
    /*
     * language 를 세팅하는 방식은 바뀔 수 있다.
     * 기본은 요청 파라미터에 넣는 방식이다. */
    protected void setLanguage(String language) {
        this.language = language;
        addRequestParam(LANGUAGE_PARAM_NAME, language);
    }
    
    /*
     * URI 를 만드는 방식을 바꿀 수 있다.
     * 예를 들어 encode() 방식을 바꿀 수 있다. */
    protected URI createUri() {
        URI uri;
        try {
            uri = this.uriComponentsBuilder.encode().build().toUri();
        } catch (Exception exception){
            throw new RuntimeException(" uri 만들기 실패 ");
        }
        return uri;
    }
    
    /*
     * Http 메서드를 바꿀 수 있다. */
    protected HttpMethod method() {
        return HttpMethod.GET;
    }
    
    // Never open
    private String getApiKey() { return apiKey; }
    
    /*
     * body() 의 기본값이 null 을 반환하므로
     * 이 메서드는 기본적으로 RequestEntity<Void> 를 반환한다.
     * B : 바디 타입*/
    private <B> RequestEntity<B> makeEntity(URI uri, HttpHeaders headers) {
        log.debug("uri: {}", uri);
        log.debug("headers: {}", headers);
        log.debug("path: {}", path);
        RequestEntity<B> entity;
        try {
            entity = (RequestEntity<B>) RequestEntity.method(method, uri).headers(headers).body(this.body);
        } catch (Exception exception) {
            throw new RuntimeException("RequestEntity<B> 캐스팅에 실패하였습니다.");
        }
        return entity;
    }
    
    private void validateApiComponents() {
        if (API_KEY_PARAM_NAME != null) {
            if (apiKey == null) {
                throw new IllegalArgumentException("Api Key was not set");
            }
        }
        if (method == null) throw new IllegalArgumentException("HttpMethod was not set");
        if (uri == null) throw new IllegalArgumentException("uri was not set");
        if (path == null) throw new IllegalArgumentException("path was not set");
    }
    
    @Override
    public String toString() {
        return "{\"AbstractAPIRequest\":{" + "\"headers\":" + headers + ", \"method\":" + ((method != null) ? ("\"" + method + "\"") : null) + ", \"body\":" + body + ", \"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + ", \"uriBase\":" + ((uriBase != null) ? ("\"" + uriBase + "\"") : null) + ", \"uri\":" + uri + "}}";
    }
}
