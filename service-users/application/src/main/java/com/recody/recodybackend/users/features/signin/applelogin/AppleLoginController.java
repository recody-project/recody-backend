package com.recody.recodybackend.users.features.signin.applelogin;

import com.recody.recodybackend.users.RecodySignInSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
class AppleLoginController {
    
    private final SetAppleTokenHandler setAppleTokenHandler;
    private final AppleLoginHandler<String> appleLoginHandler;
    
    @PostMapping( "/api/v1/users/sign-in/apple" )
    @ResponseBody
    public RecodySignInSession appleLogin(@RequestBody AppleLoginRequestBody body) {
        String userIdentifier = body.getUserIdentifier();
        RecodySignInSession session = appleLoginHandler.handle( userIdentifier );
        log.info( "userIdentifier: {}", userIdentifier );
        return session;
    }
    
    @PostMapping( "/api/v1/users/signup/apple" )
    @ResponseBody
    public String setAppleUserIdentifier(@RequestBody SetAppleUserIdentifierRequestBody body) {
        String userIdentifier = body.getUserIdentifier();
        String email = body.getEmail();
        log.info( "userIdentifier: {}", userIdentifier );
        return setAppleTokenHandler.handle( email, userIdentifier );
    }
    
    @GetMapping( "/signin/apple" )
    public String appleLoginPage(@RequestParam String att, HttpServletRequest request) {
        request.setAttribute( "received", att );
        
        
        return "login";
    }
    
    @GetMapping( "/test/red" )
    @ResponseBody
    public String redTest(HttpServletResponse response, RedirectAttributes ra) throws IOException {
        ra.addAttribute( "att", "att1" );
        response.sendRedirect( "/signin/apple?att=attt1" );
        
        return "";
    }
    
}
