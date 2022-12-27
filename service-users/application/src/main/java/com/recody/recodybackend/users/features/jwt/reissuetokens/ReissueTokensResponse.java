package com.recody.recodybackend.users.features.jwt.reissuetokens;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReissueTokensResponse {
    private String accessToken;
    private String accessExpireTime;
    
    @Override
    public String toString() {
        return "{\"ReissueTokensResponse\":{" + "\"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + ", \"accessExpireTime\":" + ((accessExpireTime != null) ? ("\"" + accessExpireTime + "\"") : null) + "}}";
    }
}
