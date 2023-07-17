package com.recody.recodybackend.users.features.signin.applelogin;

import com.recody.recodybackend.users.data.RecodyUserEntity;
import com.recody.recodybackend.users.data.RecodyUserRepository;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

public interface SetAppleTokenHandler {
    
    String handle(String email, String userIdentifier);
   
    @Component
    @RequiredArgsConstructor
    @Slf4j
    class DefaultSetAppleTokenHAndler implements SetAppleTokenHandler {
        private final RecodyUserRepository recodyUserRepository;
       
        @Override
        @Transactional
        public String handle(String email, String userIdentifier) {
            RecodyUserEntity recodyUserEntity1 = recodyUserRepository.findByEmail( email )
                                                                     .orElseThrow(
                                                                             UserNotFoundException::new );
            recodyUserEntity1.setAppleUserIdentifier( userIdentifier );
            return userIdentifier;
        }
    }
}
