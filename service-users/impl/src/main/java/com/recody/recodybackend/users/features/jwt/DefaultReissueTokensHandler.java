package com.recody.recodybackend.users.features.jwt;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.data.RefreshTokenEntity;
import com.recody.recodybackend.users.data.RefreshTokenRepository;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultReissueTokensHandler implements ReissueTokensHandler{
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtManager jwtManager;
    
    @Override
    @Transactional
    public ReissueTokensResponse handle(ReissueTokens command) {
        log.debug("handling {}", command);
        RefreshTokenEntity oldRefreshToken = refreshTokenRepository.findByRefreshTokenValue(command.getRefreshToken());
        log.debug("oldRefreshToken: {}", oldRefreshToken);
        if (oldRefreshToken == null) {
            throw new ApplicationException(UsersErrorType.RefreshTokenNotFound, HttpStatus.NOT_FOUND);
        }
        if (!oldRefreshToken.getUserAgent().equals(command.getUserAgent())){
            throw new ApplicationException(UsersErrorType.InvalidUserAgentValue, HttpStatus.BAD_REQUEST);
        }
        // 액세스 토큰 재발급
        String newAccessToken = jwtManager.createAccessToken(CreateAccessToken.builder()
                                                                              .email(oldRefreshToken.getSubject())
                                                                                .build());
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
