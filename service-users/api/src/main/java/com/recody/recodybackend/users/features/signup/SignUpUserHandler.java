package com.recody.recodybackend.users.features.signup;

/**
 * 기본적인 회원가입을 처리한다.
 * 현재는 어드민 유저 회원가입에만 사용함. */
public interface SignUpUserHandler {
    
    Long handle(SignUpUser command);
    
}
