package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.users.RecodyUserEmail;
import com.recody.recodybackend.users.RecodyUserInfo;

/**
 * 기본적인 회원가입을 처리한다.
 * 현재는 어드민 유저 회원가입에만 사용함. */
public interface SignUpUserHandler {
    
    RecodyUserInfo handle(SignUpUser command);
    
    boolean checkDuplicateEmail(RecodyUserEmail email);
    
}
