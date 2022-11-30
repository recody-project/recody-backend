package com.recody.recodybackend.users;

import lombok.Data;

@Data
public class ReissueTokensRequest {
    
    private String refreshToken;
}
