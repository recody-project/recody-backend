package com.recody.recodybackend.users.features.jwt;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ErrorType;
import com.recody.recodybackend.users.data.RefreshTokenEntity;
import com.recody.recodybackend.users.data.RefreshTokenRepository;
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
            throw new ApplicationException(ErrorType.RefreshTokenNotFound, HttpStatus.NOT_FOUND);
        }
        if (!oldRefreshToken.getUserAgent().equals(command.getUserAgent())){
            throw new ApplicationException(ErrorType.InvalidUserAgentValue, HttpStatus.BAD_REQUEST);
        }
        // 리프레시 토큰 발급
        String newAccessToken = jwtManager.createAccessToken(CreateAccessToken.builder().email(oldRefreshToken.getSubject())
                                                                           .build());
        String newRefreshToken = jwtManager.createRefreshToken(CreateRefreshToken.builder().email(oldRefreshToken.getSubject())
                                                                           .build());
        // 리프레시 토큰을 저장한다.
        // TODO: 이전 refresh token 을 삭제하는 로직
        saveNewRefreshToken(oldRefreshToken, newRefreshToken);
        ReissueTokensResponse response = createResponse(newAccessToken, newRefreshToken);
        log.info("재발급한 토큰을 반환합니다.: {}", response);
        return response;
    }
    
    private void saveNewRefreshToken(RefreshTokenEntity refreshToken, String newRefreshToken) {
        refreshTokenRepository.save(RefreshTokenEntity.builder().refreshTokenValue(newRefreshToken).subject(refreshToken.getSubject()).userAgent(
                refreshToken.getUserAgent()).build());
    }
    
    private ReissueTokensResponse createResponse(String newAccessToken, String newRefreshToken) {
        return ReissueTokensResponse.builder()
                                    .accessToken(newAccessToken)
                                    .refreshToken(newRefreshToken)
                                    .accessExpireTime(jwtManager.getExpireTimeFromToken(newAccessToken))
                                    .refreshExpireTime(jwtManager.getExpireTimeFromToken(newRefreshToken)).build();
    }
}
