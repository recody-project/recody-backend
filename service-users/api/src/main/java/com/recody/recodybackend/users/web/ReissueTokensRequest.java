package com.recody.recodybackend.users.web;

import lombok.Data;

@Data
public class ReissueTokensRequest {
    
    private String refreshToken;
}
