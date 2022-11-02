package com.recody.recodybackend.users.features.login;

import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.commonbootutils.jwt.CreateAccessToken;
import com.recody.recodybackend.commonbootutils.jwt.CreateRefreshToken;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserMapper;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import com.recody.recodybackend.users.features.generatenickname.NicknameGenerator;
import com.recody.recodybackend.users.features.login.membership.AdminUserInfo;
import com.recody.recodybackend.users.features.login.membership.MembershipManager;
import com.recody.recodybackend.users.features.login.membership.RecodySignInSession;
import com.recody.recodybackend.users.RecodyUserInfo;
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
    private final RecodyUserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;
    private final RecodyUserMapper mapper;
    
    
    @Override
    public boolean exists(String email) {
        Optional<RecodyUser> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }
    
    @Override
    public RecodyUserInfo signUp(OAuthUserInfo userInfo) {
        
        Optional<RecodyUser> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        RecodyUser recodyUser;
        if (optionalUser.isEmpty()) {
            recodyUser = doSignup(userInfo);
            log.info("회원가입 되었습니다.: {}", recodyUser);
        } else {
            recodyUser = optionalUser.get();
        }
        return mapper.map(recodyUser);
    }
    
    @Override
    public RecodyUserInfo signUpAdmin(AdminUserInfo userInfo) {
        return null;
    }
    
    @Override
    public RecodySignInSession signIn(RecodyUserInfo recodyUserInfo) {
        RecodySignInSession signInSession = doSignIn(recodyUserInfo);
        
        
        log.info("{} 님을 로그인 처리하였습니다.", recodyUserInfo.getName());
        return signInSession;
    }
    
    @Override
    public RecodySignInSession signIn(OAuthUserInfo userInfo) {
        // 이메일과 소셜 정보를 사용해 유저 정보를 가져온다.
        Optional<RecodyUser> optionalUser = userRepository.findByEmail(userInfo.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        // 유저정보를 사용해 응답을 만들어 반환한다.
        RecodyUserInfo recodyUserInfo = mapper.map(optionalUser.get());
        RecodySignInSession session = doSignIn(recodyUserInfo);
        log.info("{} 님을 로그인 처리하였습니다.", userInfo.getName());
        return session;
    }
    
    private RecodySignInSession doSignIn(RecodyUserInfo recodyUserInfo) {
        String accessToken = jwtManager.createAccessToken(CreateAccessToken
                                                                  .builder()
                                                                  .userId(recodyUserInfo.getUserId())
                                                                  .email(recodyUserInfo.getEmail())
                                                                  .build());
        String refreshToken = jwtManager.createRefreshToken(CreateRefreshToken
                                                                    .builder()
                                                                    .userId(recodyUserInfo.getUserId())
                                                                    .email(recodyUserInfo.getEmail())
                                                                    .build());
        return RecodySignInSession
                .builder()
                .socialType(recodyUserInfo.getSocialType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessExpireTime(jwtManager.getExpireTimeFromToken(accessToken))
                .refreshExpireTime(jwtManager.getExpireTimeFromToken(refreshToken))
                .build();
    }
    
    private RecodyUser doSignup(OAuthUserInfo userInfo) {
        RecodyUser targetUser;
        targetUser = RecodyUser
                .builder()
                .email(userInfo.getEmail())
                .socialType(userInfo.getSocialProvider())
                .nickname(nicknameGenerator.randomNickname())
                .role(Role.ROLE_MEMBER)
                .pictureUrl(userInfo.getProfileImageUrl())
                .name(userInfo.getName())
                .build();
        RecodyUser savedUser;
        try {
            savedUser = userRepository.save(targetUser);
        } catch (PersistenceException exception) {
            log.error("저장 에러 exception: {}", exception.getMessage());
            throw new InternalServerError(exception.getMessage());
        }
        return savedUser;
    }
}
