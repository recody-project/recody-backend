package com.recody.recodybackend.users.features.login;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class ProcessLogin {
    private String accessToken;
    private String userAgent;
    
    @Override
    public String toString() {
        return "{\"ProcessLogin\":{" + "\"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + ", \"userAgent\":" + ((userAgent != null) ? ("\"" + userAgent + "\"") : null) + "}}";
    }
}
