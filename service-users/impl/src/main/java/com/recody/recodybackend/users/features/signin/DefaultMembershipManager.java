package com.recody.recodybackend.users.features.signin;

import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.commonbootutils.jwt.CreateAccessToken;
import com.recody.recodybackend.commonbootutils.jwt.CreateRefreshToken;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.RecodySignInSession;
import com.recody.recodybackend.users.RecodyUserInfo;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.jwt.refreshtoken.RefreshTokenManager;
import com.recody.recodybackend.users.features.signin.membership.AdminUserInfo;
import com.recody.recodybackend.users.features.signin.membership.MembershipManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
class DefaultMembershipManager implements MembershipManager {
    
    private final JwtManager jwtManager;
    private final RecodyUserRepository recodyUserRepository;
    private final NicknameGenerator nicknameGenerator;
    private final RecodyUserMapper recodyUserMapper;
    private final RefreshTokenManager refreshTokenManager;
    
    
    @Override
    public boolean exists(String email) {
        Optional<RecodyUserEntity> optionalUser = recodyUserRepository.findByEmail( email );
        return optionalUser.isPresent();
    }
    
    @Override
    public RecodyUserInfo signUp(OAuthUserInfo userInfo) {
        
        Optional<RecodyUserEntity> optionalUser = recodyUserRepository.findByEmail( userInfo.getEmail() );
        RecodyUserEntity recodyUserEntity;
        if ( optionalUser.isEmpty() ) {
            recodyUserEntity = doSignup( userInfo );
            log.info( "회원가입 되었습니다.: {}", recodyUserEntity );
        }
        else {
            recodyUserEntity = optionalUser.get();
        }
        return recodyUserMapper.map( recodyUserEntity );
    }
    
    
    @Override
    public RecodyUserInfo signUpAdmin(AdminUserInfo userInfo) {
        // 필요할 때 구현합니다.
        return null;
    }
    
    public RecodySignInSession createSessionInfo(RecodyUserInfo recodyUserInfo, String userAgent) {
        String accessToken = jwtManager.createAccessToken( CreateAccessToken
                                                                   .builder()
                                                                   .userId( recodyUserInfo.getUserId() )
                                                                   .email( recodyUserInfo.getEmail() )
                                                                   .build() );
        String refreshToken = jwtManager.createRefreshToken( CreateRefreshToken
                                                                     .builder()
                                                                     .userId( recodyUserInfo.getUserId() )
                                                                     .email( recodyUserInfo.getEmail() )
                                                                     .build() );
        RecodySignInSession session = RecodySignInSession
                                            .builder()
                                            .socialType( recodyUserInfo.getSocialType() )
                                            .role( recodyUserInfo.getRole() )
                                            .accessToken( accessToken )
                                            .refreshToken( refreshToken )
                                            .accessExpireTime( jwtManager.getExpireTimeFromToken( accessToken ) )
                                            .refreshExpireTime( jwtManager.getExpireTimeFromToken( refreshToken ) )
                                            .build();
        refreshTokenManager.integrate( session, userAgent );
        return session;
    }
    
    private RecodyUserEntity doSignup(OAuthUserInfo userInfo) {
        RecodyUserEntity targetUser;
        targetUser = RecodyUserEntity
                             .builder()
                             .email( userInfo.getEmail() )
                             .socialType( userInfo.getSocialProvider() )
                             .nickname( nicknameGenerator.randomNickname() )
                             .role( Role.ROLE_MEMBER )
                             .pictureUrl( userInfo.getProfileImageUrl() )
                             .name( userInfo.getName() )
                             .build();
        RecodyUserEntity savedUser;
        try {
            savedUser = recodyUserRepository.save( targetUser );
        } catch ( PersistenceException exception ) {
            log.error( "저장 에러 exception: {}", exception.getMessage() );
            throw new InternalServerError( exception.getMessage() );
        }
        return savedUser;
    }
}
