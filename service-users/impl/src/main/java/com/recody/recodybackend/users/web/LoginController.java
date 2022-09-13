package com.recody.recodybackend.users.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.users.features.login.ProcessLogin;
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
    public ResponseEntity<SuccessResponseBody> loginNaver(@RequestHeader("User-Agent") String userAgent,
                                                          @RequestBody SocialLoginRequest request,
                                                          HttpServletRequest httpRequest) {
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("users.login.succeeded", null, httpRequest.getLocale()))
                                         .data(socialLoginService.handleNaverLogin(
                                                             ProcessLogin
                                                                 .builder()
                                                                 .resourceAccessToken(request.getResourceAccessToken())
                                                                 .userAgent(userAgent)
                                                                 .build()))
                                         .build());
    }
    
    @PostMapping("/api/v1/login/google")
    public ResponseEntity<SuccessResponseBody> loginGoogle(@RequestHeader("User-Agent") String userAgent,
                                                           @RequestBody SocialLoginRequest request,
                                                           HttpServletRequest httpRequest) {
        return ResponseEntity.ok(SuccessResponseBody
                                         .builder()
                                         .message(ms.getMessage("users.login.succeeded", null, httpRequest.getLocale()))
                                         .data(socialLoginService.handleGoogleLogin(ProcessLogin
                                                                                      .builder()
                                                                                      .resourceAccessToken(request.getResourceAccessToken())
                                                                                      .userAgent(userAgent)
                                                                                      .build()))
                                         .build());
    }
}
