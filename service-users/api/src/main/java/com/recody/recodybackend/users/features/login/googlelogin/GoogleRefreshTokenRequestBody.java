package com.recody.recodybackend.users.features.login.googlelogin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;

// kk
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleRefreshTokenRequestBody {
    
    @JsonProperty(value = "client_id")
    private String client_id;
    
    @JsonProperty(value = "refresh_token")
    private String refresh_token;
    
    @Builder.Default
    @JsonProperty(value = "grant_type")
    private String grant_type = "refresh_token";
    
    
    public MultiValueMap<String, String> toMultiValueMap() throws IllegalAccessException {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        Field[] declaredFields = getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            Object value = declaredField.get(this);
            map.add(name, (String) value);
        }
        return map;
    }
    
    
    @Override
    public String toString() {
        return "{\"GoogleRefreshTokenRequestBody\":{" + "\"clientId\":" + ((client_id != null) ? ("\"" + client_id + "\"") : null) + ", \"refreshToken\":" + ((refresh_token != null) ? ("\"" + refresh_token + "\"") : null) + ", \"grantType\":" + ((grant_type != null) ? ("\"" + grant_type + "\"") : null) + "}}";
    }
    
}
