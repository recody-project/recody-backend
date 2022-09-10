package com.recody.recodybackend.common.openapi;

/*
* T 타입으로 만드는 api 결과
* 뽑아낼 데이터를 메서드로 선언하고 구현한다. */
public interface ApiResponse<T> {
    
    T getResponse();
}
