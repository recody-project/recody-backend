package com.recody.recodybackend.common.openapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
public abstract class AbstractAPIRequest implements APIRequest {
    
    private final UriComponentsBuilder uriComponentsBuilder;
    private final HttpHeaders headers = new HttpHeaders();
    private final String baseUrl;
    private final Map<String, String> requestParams = new HashMap<>();
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private HttpMethod method;
    private Object body;
    private URI uri;
    private boolean isUriBuilt = false;
    protected String path;
    
    protected AbstractAPIRequest(String baseUrl) {
        this.baseUrl = baseUrl;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        this.body = body();
        this.method = method();
    }
    
    public void setBody(Object body) {
        this.body = body;
    }
    
    public void setMethod(HttpMethod method) {
        this.method = method;
    }
    
    public final void addHeader(String key, String value) {
        headers.add(key, value);
    }
    
    /*
     * B : 바디 타입
     * 이 메서드를 호출하면 모든 uri, method, body, header 가 세팅되어 반환된다.
     */
    public final <B> RequestEntity<B> toEntity() {
        // uri 경로 세팅 (null 이면 무시)
        setPath();
        // 파라미터 세팅
        setParameters();
        // uri 생성
        URI uri = consolidateUri();
        this.uri = uri;
        // validates all uri components
        validateApiComponents();
        return makeEntity(uri, headers);
    }
    
    /*
    * mandatory default: true
    * 요청 파라미터를 추가할 때 null 이면 예외를 던진다. */
    public final void addRequestParam(String key, String value) { doAddRequestParam(key, value, true); }
    
    public final void addRequestParam(String key, String value, boolean mandatory) { doAddRequestParam(key, value, mandatory); }
    
    /*
     * 원하는 body 를 넣을 수 있다.
     * 기본 값은 null 로, toEntity() 가 RequestEntity<Void> 를 반환하게 된다. */
    public Object body() { return null; }
    
    public final void basicAuth(String username, String password) {
        this.headers.setBasicAuth(username, password);
    }
    
    @Override
    public final void setPath(String path) {
        this.path = path;
    }
    
    
    /* ==================== Protected / Private Methods ===================== */
    
    
    private void setPath() {
        if (path == null) {
            return;
        }
        this.uriComponentsBuilder.path(path);
    }
    
    /*
     * URI 를 만드는 방식을 바꿀 수 있다.
     * 예를 들어 encode() 방식을 바꿀 수 있다. */
    protected URI consolidateUri() {
        if (isUriBuilt){
            log.warn("URI 를 두번 만드려는 시도");
            return this.uri;
        }
        URI uri;
        try {
            uri = this.uriComponentsBuilder.encode().build().toUri();
            this.isUriBuilt = true;
        } catch (Exception exception) {
            throw new RuntimeException(" uri 만들기 실패 ");
        }
        return uri;
    }
    
    
    /*
     * Http 메서드를 바꿀 수 있다. */
    protected HttpMethod method() {
        return HttpMethod.GET;
    }
    
    private void doAddRequestParam(String key, String value, boolean mandatory) {
        if (!mandatory) {
            if (key == null) {
                log.debug("요청 파라미터 key 가 null 입니다.");
                return;
            }
            if (value == null) {
                log.debug("요청 파라미터 value 가 null 입니다. key: {}", key);
                return;
            }
        } else {
            if (key == null) {
                log.warn("요청 파라미터 key 가 null 입니다.");
                return;
            }
            if (value == null) {
                log.warn("요청 파라미터 value 가 null 입니다. key: {}", key);
                throw new IllegalArgumentException("요청 파라미터 value 가 null 입니다. key: " + key);
            }
        }
        requestParams.put(key, value);
    }
    
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
    
    private void setParameters() {
        for (String key : requestParams.keySet()) {
            String value = requestParams.get(key);
            if (value == null) {
                log.warn("value 가 null 입니다: key: {}, value: {}", key, null);
                throw new RuntimeException("value 가 null 입니다.");
            }
            uriComponentsBuilder.queryParam(key, value);
        }
    }
    
    private void validateApiComponents() {
        if (method == null) throw new IllegalArgumentException("HttpMethod was not set");
        if (uri == null) throw new IllegalArgumentException("uri was not set");
    }
}
