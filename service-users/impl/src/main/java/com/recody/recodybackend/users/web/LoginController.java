package com.recody.recodybackend.users.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import com.recody.recodybackend.users.*;
import com.recody.recodybackend.users.features.allusers.FetchAllUsers;
import com.recody.recodybackend.users.features.allusers.FetchAllUsersHandler;
import com.recody.recodybackend.users.features.getuserinfo.GetUserInfo;
import com.recody.recodybackend.users.features.getuserinfo.GetUserInfoHandler;
import com.recody.recodybackend.users.features.jwt.reissuetokens.ReissueTokens;
import com.recody.recodybackend.users.features.jwt.reissuetokens.ReissueTokensHandler;
import com.recody.recodybackend.users.features.sendresetemail.SendResetEmail;
import com.recody.recodybackend.users.features.sendresetemail.SendResetEmailHandler;
import com.recody.recodybackend.users.features.signin.ProcessSocialLogin;
import com.recody.recodybackend.users.features.signin.SocialLoginService;
import com.recody.recodybackend.users.features.signin.adminsignin.SignInAdminUser;
import com.recody.recodybackend.users.features.signin.adminsignin.SignInAdminUserHandler;
import com.recody.recodybackend.users.features.signin.membersignin.SignInUser;
import com.recody.recodybackend.users.features.signin.membersignin.SignInUserHandler;
import com.recody.recodybackend.users.features.signup.GetUserInfoResponse;
import com.recody.recodybackend.users.features.signup.SignUpUser;
import com.recody.recodybackend.users.features.signup.SignUpUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class LoginController {
    
    private final MessageSource ms;
    private final SocialLoginService socialLoginService;
    private final ReissueTokensHandler reissueTokensHandler;
    private final SignUpUserHandler signUpUserHandler;
    private final SignInAdminUserHandler signInAdminUserHandler;
    private final SignInUserHandler signInUserHandler;
    
    private final GetUserInfoHandler getUserInfoHandler;
    
    private final FetchAllUsersHandler fetchAllUsersHandler;
    
    private final SendResetEmailHandler sendResetEmailHandler;
    
    private final JwtManager jwtManager;
    
    @PostMapping( "/api/v1/users/signup/check-duplicate" )
    public ResponseEntity<SuccessResponseBody> existsEmail(@RequestBody CheckDuplicateEmailRequest request,
                                                           HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody
                        .builder()
                        .message( ms.getMessage( "users.signup.email.check-duplicate.succeeded", null,
                                                 httpRequest.getLocale() ) )
                        .data( new CheckDuplicateEmailResponse(
                                signUpUserHandler.checkDuplicateEmail( RecodyUserEmail.of( request.getEmail() ) ) ) )
                        .build()
                                );
    }
    
    /**
     * 구글 로그인을 진행한다.
     * 성공시
     * - 액세스토큰, 리프레시 토큰을 반환한다.
     * 실패시
     * - SocialLoginFailed
     */
    @PostMapping( "/api/v1/login/google" )
    public ResponseEntity<SuccessResponseBody> loginGoogle(@RequestHeader( "User-Agent" ) String userAgent,
                                                           @RequestBody SocialLoginRequest request,
                                                           HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody
                                          .builder()
                                          .message( ms.getMessage( "users.sign-in.succeeded", null, httpRequest.getLocale() ) )
                                          .data( socialLoginService.handleGoogleLogin( ProcessSocialLogin
                                                                                               .builder()
                                                                                               .resourceAccessToken(
                                                                                                       request.getResourceAccessToken() )
                                                                                               .resourceRefreshToken(
                                                                                                       request.getResourceRefreshToken() )
                                                                                               .userAgent( userAgent )
                                                                                               .build() ) )
                                          .build() );
    }
    
    @PostMapping( "/api/v1/login/naver" )
    public ResponseEntity<SuccessResponseBody> loginNaver(@RequestHeader( "User-Agent" ) String userAgent,
                                                          @RequestBody SocialLoginRequest request,
                                                          HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody
                                          .builder()
                                          .message( ms.getMessage( "users.sign-in.succeeded", null, httpRequest.getLocale() ) )
                                          .data( socialLoginService.handleNaverLogin(
                                                  ProcessSocialLogin
                                                          .builder()
                                                          .resourceAccessToken( request.getResourceAccessToken() )
                                                          .resourceRefreshToken( request.getResourceRefreshToken() )
                                                          .userAgent( userAgent )
                                                          .build() ) )
                                          .build() );
    }
    
    /**
     * 리프레시 토큰으로 액세스 토큰을 갱신한다.
     * 성공시
     * - 새로운 액세스 토큰을 반환한다.
     * 실패시
     * - RefreshTokenExpired
     */
    @PostMapping( "/api/v1/token/refresh" )
    public ResponseEntity<SuccessResponseBody> refresh(@RequestHeader( "User-Agent" ) String userAgent,
                                                       @RequestBody ReissueTokensRequest request,
                                                       HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody.builder()
                                                     .message( ms.getMessage( "users.tokens.refresh.succeeded", null,
                                                                              httpRequest.getLocale() ) )
                                                     .data( reissueTokensHandler.handle( ReissueTokens.builder()
                                                                                                      .refreshToken(
                                                                                                              request.getRefreshToken() )
                                                                                                      .userAgent( userAgent ).build() ) )
                                                     .build() );
    }
    
    @PostMapping( "/api/v1/users/sign-in" )
    public ResponseEntity<SuccessResponseBody> signIn(@RequestBody SignInAdminUser command,
                                                      HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody
                                          .builder()
                                          .message( ms.getMessage( "users.admin.sign-in.succeeded", null, httpRequest.getLocale() ) )
                                          .data( signInAdminUserHandler.handle( command ) ).build() );
    }
    
    @PostMapping( "/api/v2/users/sign-in" )
    public ResponseEntity<SuccessResponseBody> signInV2(@RequestBody SignInUserRequest request,
                                                        @RequestHeader( value = "User-Agent" ) String userAgent,
                                                        HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody
                        .builder()
                        .message( ms.getMessage( "users.sign-in.succeeded", null, httpRequest.getLocale() ) )
                        .data( SignInUserResponse
                                       .builder()
                                       .signInInfo(
                                               signInUserHandler.handle( SignInUser
                                                                                 .builder()
                                                                                 .email( RecodyUserEmail.of( request.getEmail() ) )
                                                                                 .password( request.getPassword() )
                                                                                 .userAgent( userAgent )
                                                                                 .build() ) )
                                       .build() )
                        .build() );
    }
    
    @PostMapping( "/api/v1/users/signup" )
    public ResponseEntity<SuccessResponseBody> signup(@Valid @RequestBody SignUpUserRequest request,
                                                      HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody
                                          .builder()
                                          .message( ms.getMessage( "users.signup.succeeded", null, httpRequest.getLocale() ) )
                                          .data( signUpUserHandler.handle( signUpUserCommand( request ) ) )
                                          .build() );
    }
    
    @PostMapping( "/api/v2/users/signup" )
    public ResponseEntity<SuccessResponseBody> signupV2(@Valid @RequestBody SignUpUserRequest request,
                                                        HttpServletRequest httpRequest) {
        return ResponseEntity.ok( SuccessResponseBody
                                          .builder()
                                          .message( ms.getMessage( "users.signup.succeeded", null, httpRequest.getLocale() ) )
                                          .data( SignUpUserResponse.builder()
                                                                   .userInfo( signUpUserHandler.handle( signUpUserCommand( request ) ) )
                                                                   .build() )
                                          .build() );
    }
    
    @GetMapping( "/api/v1/users/info" )
    public ResponseEntity<SuccessResponseBody> getUserInfo(HttpServletRequest httpRequest,
                                                           @AccessToken String accessToken) {
        return ResponseEntity.ok(
                SuccessResponseBody
                        .builder()
                        .message( ms.getMessage( "users.info.get.succeeded", null, httpRequest.getLocale() ) )
                        .data( GetUserInfoResponse.builder()
                                                  .userInfo( getUserInfoHandler.handle(
                                                          GetUserInfo.builder()
                                                                     .userId( jwtManager.resolveUserId( accessToken ) )
                                                                     .build() ) )
                                                  .build() )
                        .build() );
    }
    
    @GetMapping( "/api/v1/users/users" )
    public ResponseEntity<FetchAllUsersResponse> getAllUsers(HttpServletRequest httpRequest,
                                                             @AccessToken String accessToken) {
        return ResponseEntity.ok(
                FetchAllUsersResponse
                        .builder()
                        .users( fetchAllUsersHandler.handle(
                                FetchAllUsers.builder()
                                             .userId( jwtManager.resolveUserId( accessToken ) )
                                             .build() ) )
                        .build() );
    }
    
    @PostMapping( "/api/v1/users/send-reset-email" )
    private ResponseEntity<SuccessResponseBody> sendResetEmail(HttpServletRequest httpRequest,
                                                               @RequestBody SendResetEmailRequest request) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "", null, "이메일을 보냈습니다.", httpRequest.getLocale() ) )
                                   .data( sendResetEmailHandler.handle(
                                           SendResetEmail.builder()
                                                         .email( request.getEmail() )
                                                         .build() ) )
                                   .build()
                                );
    }
    
    private static SignUpUser signUpUserCommand(SignUpUserRequest request) {
        return SignUpUser
                       .builder()
                       .name( request.getName() )
                       .nickname( request.getNickname() )
                       .email( RecodyUserEmail.of( request.getEmail() ) )
                       .password( request.getPassword() )
                       .passwordConfirm( request.getPasswordConfirm() )
                       .pictureUrl( request.getPictureUrl() )
                       .build();
    }
}
