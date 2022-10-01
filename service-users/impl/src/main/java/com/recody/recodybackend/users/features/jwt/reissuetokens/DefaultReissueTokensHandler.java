package com.recody.recodybackend.users.features.jwt.reissuetokens;

import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.users.features.jwt.refreshtoken.RefreshTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultReissueTokensHandler implements ReissueTokensHandler {
    
    private final RefreshTokenManager refreshTokenManager;
    private final JwtManager jwtManager;
    
    @Override
    public ReissueTokensResponse handle(ReissueTokens command) {
        log.debug("handling {}", command);
        String newAccessToken = refreshTokenManager.reissue(command.getRefreshToken(), command.getUserAgent());
        ReissueTokensResponse response = createResponse(newAccessToken);
        log.info("재발급한 토큰을 반환합니다.: {}", response);
        return response;
    }
    
    private ReissueTokensResponse createResponse(String newAccessToken) {
        return ReissueTokensResponse.builder()
                                    .accessToken(newAccessToken)
                                    .accessExpireTime(jwtManager.getExpireTimeFromToken(newAccessToken)).build();
    }
}
