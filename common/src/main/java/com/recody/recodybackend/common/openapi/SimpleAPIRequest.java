package com.recody.recodybackend.common.openapi;

public class SimpleAPIRequest extends AbstractAPIRequest{
    public SimpleAPIRequest(String baseUri, String path) {
        super(baseUri, path);
    }
    
    public SimpleAPIRequest(String baseUri) {
        super(baseUri);
    }
}
