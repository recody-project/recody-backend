package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;

/*
* RequestEntity 로 바꿔서 요청을 보내는 객체.
* 반환 타입은 아래와 같다.
* */
public interface APIRequester<T extends APIRequest>{
    
    /*
    * APIRequest 인터페이스를 직접 상속했을 때 사용됨 */
    String executeToString(T request);
    JsonNode executeToJsonNode(T request);
    JsonAPIResponse executeToJson(T request);
    
    /*
    * APIRequest 객체를 delegate 로 사용할 때 사용됨. */
    String requestToString(Request request);
    JsonNode requestToJsonNode(Request request);
    JsonAPIResponse requestToJson(Request request);
    <S> S requestAndGet(Request request, Class<S> clazz);
    
}
