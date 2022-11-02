package com.recody.recodybackend.users;

import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.users.exceptions.UsersErrorType;
import lombok.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Value(staticConstructor = "of")
public class RecodyUserEmail {
    
    String email;
    
    private RecodyUserEmail(String email) {
        requireNonNull(email);
        this.email = email.trim();
    }
    
    private void requireNonNull(String email) {
        if ( !StringUtils.hasText( email ) || ObjectUtils.isEmpty( email ) ) {
            throw ApplicationExceptions.badRequestOf( UsersErrorType.EmailShouldNotBeNull );
        }
    }
}
