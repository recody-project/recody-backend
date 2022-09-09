package com.recody.recodybackend.common.openapi;

import com.fasterxml.jackson.databind.JsonNode;

public interface ApiRequester<T extends AbstractRequestEntity> {
    
    String executeToString(T request);
    
    JsonNode executeToJsonNode(T request);
    
    JsonApiResponse executeToJson(T request);
    
}
