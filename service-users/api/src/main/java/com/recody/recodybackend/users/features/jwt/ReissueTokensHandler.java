package com.recody.recodybackend.users.features.jwt;

public interface ReissueTokensHandler {
    
    ReissueTokensResponse handle(
            ReissueTokens command);
}
