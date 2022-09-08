package com.recody.recodybackend.common.openapi;

import com.recody.recodybackend.common.contents.ContentSource;

/*
* T 타입으로 만드는 api 결과 */
public interface ApiResponse<T> {
    
    ContentSource getContentSource();
    
    T getResponse();
}
