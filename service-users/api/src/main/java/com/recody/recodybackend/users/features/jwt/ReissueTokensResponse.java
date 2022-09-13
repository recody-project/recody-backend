package com.recody.recodybackend.users.features.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReissueTokensResponse {
    private String accessToken;
    private String refreshToken;
    private String accessExpireTime;
    private String refreshExpireTime;
    
    @Override
    public String toString() {
        return "{\"ReissueTokensResponse\":{" + "\"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + ", \"refreshToken\":" + ((refreshToken != null) ? ("\"" + refreshToken + "\"") : null) + ", \"accessExpireTime\":" + ((accessExpireTime != null) ? ("\"" + accessExpireTime + "\"") : null) + ", \"refreshExpireTime\":" + ((refreshExpireTime != null) ? ("\"" + refreshExpireTime + "\"") : null) + "}}";
    }
}
