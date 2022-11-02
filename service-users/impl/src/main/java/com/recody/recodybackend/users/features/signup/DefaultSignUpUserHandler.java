package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import com.recody.recodybackend.users.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultSignUpUserHandler implements SignUpUserHandler {
    
    private final RecodyUserRepository recodyUserRepository;
    
    @Override
    public Long handle(SignUpUser command) {
        String email = command.getEmail();
        RecodyUser foundUser = recodyUserRepository.getByEmail(email);
        if (foundUser != null) {
            throw new ApplicationException(UsersErrorType.UserAlreadyExists, HttpStatus.BAD_REQUEST,
                                           "이미 존재하는 유저입니다. 로그인을 시도하세요.");
        }
        RecodyUser newUser = RecodyUser
                .builder()
                .username(command.getUsername())
                .email(email)
                .role(Role.ROLE_ADMIN)
                .socialType(SocialProvider.NONE)
                .password(command.getPassword())
                .nickname(command.getNickname())
                .build();
        RecodyUser savedUser = recodyUserRepository.save(newUser);
        return savedUser.getUserId();
    }
    
}
