package com.recody.recodybackend.users.features.jwt.reissuetokens;

public interface ReissueTokensHandler {
    /*
    * 리프레시 토큰을 받아 db 에 있는지 확인하고 액세스 토큰을 반환한다. */
    ReissueTokensResponse handle(
            ReissueTokens command);
}
