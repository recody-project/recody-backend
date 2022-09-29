package com.recody.recodybackend.common.openapi;

public interface APIRequestFactory<T extends APIRequest> {
    
    T newRequest();
    
}
