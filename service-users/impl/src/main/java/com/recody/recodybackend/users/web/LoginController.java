package com.recody.recodybackend.users.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.users.features.login.ProcessLogin;
import com.recody.recodybackend.users.features.login.SocialLoginRequest;
import com.recody.recodybackend.users.features.login.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class LoginController {
    
    private final MessageSource ms;
    private final SocialLoginService socialLoginService;
    
    @PostMapping("/api/v1/login/naver")
    public ResponseEntity<SuccessResponseBody> loginNaver(@RequestBody SocialLoginRequest request,
                                                          HttpServletRequest httpRequest){
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("users.login.succeeded", null, httpRequest.getLocale()))
                                                    .data(socialLoginService.loginNaver(request)).build());
    }
    
    @PostMapping("/api/v1/login/google")
    public ResponseEntity<SuccessResponseBody> loginGoogle(@RequestHeader("User-Agent") String userAgent,
                                                           @RequestBody SocialLoginRequest request,
                                                           HttpServletRequest httpRequest) {
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("users.login.succeeded", null, httpRequest.getLocale()))
                                                    .data(socialLoginService.loginGoogle(ProcessLogin
                                                                                                 .builder()
                                                                                                 .accessToken(request.getSocialAccessToken())
                                                                                                 .userAgent(userAgent).build())).build());
    }
}
