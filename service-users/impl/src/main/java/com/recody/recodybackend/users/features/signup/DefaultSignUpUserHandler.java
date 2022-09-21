package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.users.data.RecodyUser;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.data.Role;
import lombok.RequiredArgsConstructor;
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
            return null;
        }
        RecodyUser newUser = RecodyUser
                .builder()
                .username(command.getUsername())
                .email(email)
                .role(Role.ROLE_ADMIN)
                .password(command.getPassword())
                .nickname(command.getNickname())
                .build();
        RecodyUser savedUser = recodyUserRepository.save(newUser);
        return savedUser.getUserId();
    }
    
}
