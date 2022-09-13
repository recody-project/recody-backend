package com.recody.recodybackend.users.features.login;

public interface SocialLoginService {
    SocialLoginResponse loginNaver(SocialLoginRequest request);
    SocialLoginResponse loginGoogle(ProcessLogin command);
}
