package com.recody.recodybackend.common.openapi;

/*
* T 타입으로 만드는 api 결과 */
public interface ApiResponse<T> {
    
    T getResponse();
}
