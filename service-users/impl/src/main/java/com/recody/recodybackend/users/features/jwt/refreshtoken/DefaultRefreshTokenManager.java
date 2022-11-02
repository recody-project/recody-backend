package com.recody.recodybackend.users.features.jwt.refreshtoken;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.commonbootutils.jwt.CreateAccessToken;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.users.data.RefreshTokenEntity;
import com.recody.recodybackend.users.data.RefreshTokenRepository;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.RecodySignInSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class DefaultRefreshTokenManager implements RefreshTokenManager{
    private final JwtManager jwtManager;
    private final RefreshTokenRepository repository;
    
    @Override
    @Transactional
    public void integrate(RecodySignInSession signInSession, String userAgent) {
        String subject = jwtManager.resolveSubject(signInSession.getAccessToken());
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                                                                  .refreshTokenValue(signInSession.getRefreshToken())
                                                                  .userAgent(userAgent)
                                                                  .subject(subject).build();
        repository.save(refreshTokenEntity);
    }
    
    @Override
    @Transactional
    public String reissue(String refreshToken, String userAgent) {
        Optional<RefreshTokenEntity> optionalRefreshToken = repository.findByRefreshTokenValue(refreshToken);
        if (optionalRefreshToken.isEmpty()) {
            throw new ApplicationException(UsersErrorType.RefreshTokenNotFound, HttpStatus.NOT_FOUND);
        }
    
        RefreshTokenEntity oldRefreshToken = optionalRefreshToken.get();
        jwtManager.validateToken(oldRefreshToken.getRefreshTokenValue());
    
        if (!oldRefreshToken.getUserAgent().equals(userAgent)){
            throw new ApplicationException(UsersErrorType.InvalidUserAgentValue, HttpStatus.BAD_REQUEST);
        }
    
        return jwtManager.createAccessToken(CreateAccessToken.builder()
                                                             .email(oldRefreshToken.getSubject())
                                                             .build());
    }
}
