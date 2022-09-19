package com.recody.recodybackend.common.openapi;

public enum AuthType {
    
    /*
     * 아무런 인증을 하지 않는다. */
    NO_AUTH,
    
    /*
    * 필요한 정보
    *   1. Token*/
    BEARER_TOKEN,
    
    /*
    * 필요한 정보
    *   1. API Key parameter name
    *   2. API Key value*/
    API_KEY_QUERY_PARAM,
    
    /*
    * 필요한 정보
    *   1. API Key header name
    *   2. API Key value*/
    API_KEY_HEADER,
    
    /*
     * 필요한 정보
     *   1. username
     *   2. password */
    BASIC_AUTH
    ;
    
}
