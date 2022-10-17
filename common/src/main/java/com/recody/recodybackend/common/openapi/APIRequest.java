package com.recody.recodybackend.common.openapi;

import org.springframework.http.RequestEntity;

public interface APIRequest{
    
    /*
    * RestTemplate 으로 요청을 보낼 때 사용하는 RequestEntity 로 반환될 수 있어야 한다.*/
    <B> RequestEntity<B> toEntity();
    
    /*
    * 헤더를 추가할 수 있다. */
    void addHeader(String key, String value);
    
    void setBaseUrl(String baseUrl);
    void basicAuth(String username, String password);
    
    /*
    * 요청 파라미터를 추가할 수 있다. */
    void addRequestParam(String key, String value, boolean mandatory);
    
    
    void addRequestParam(String key, String value);
    
    void setPath(String path);
    
    /*
    * Body 를 추가할 수 있다. 기본값은 null 을 반환한다.
    * 따라서 RequestEntity 의 기본값은 RequestEntity<Void> 이다. */
    Object body();
}
