package com.recody.recodybackend.common.openapi;

import java.util.Objects;

public interface AuthenticationStrategy {
    
    void authenticate(APIRequest apiRequest);
    
    
    class BasicAuth implements AuthenticationStrategy {
        private final String username;
        private final String password;
        
        
        public BasicAuth(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        
        @Override
        public void authenticate(APIRequest apiRequest) {
            Objects.requireNonNull(username, "username should not be null");
            Objects.requireNonNull(password, "password should not be null");
    
            apiRequest.basicAuth(username, password);
        }
    }
    
    class NoAuth implements AuthenticationStrategy {
        
        public NoAuth() {
        }
        
        
        @Override
        public void authenticate(APIRequest apiRequest) {
        }
    }
    
    class Bearer implements AuthenticationStrategy {
        
        private static final String AUTHORIZATION = "Authorization";
        
        private final String bearerToken;
        
        
        public Bearer(String bearerToken) {
            Objects.requireNonNull(bearerToken, "bearerToken should not be null");
            this.bearerToken = bearerToken;
        }
        
        
        @Override
        public void authenticate(APIRequest apiRequest) {
            apiRequest.addHeader(AUTHORIZATION, "Bearer " + bearerToken);
        }
    }
    
    class ApiKeyRequestParam implements AuthenticationStrategy {
        
        private final String KEY;
        private final String VALUE;
        
        
        public ApiKeyRequestParam(String key, String value) {
            Objects.requireNonNull(key, "key should not be null");
            Objects.requireNonNull(value, "value should not be null");
            this.KEY = key;
            this.VALUE = value;
        }
        
        
        @Override
        public void authenticate(APIRequest apiRequest) {
            apiRequest.addRequestParam(KEY, VALUE);
        }
    }
    
}
