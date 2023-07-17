package com.recody.recodybackend.users.features.signin.applelogin;

import com.recody.recodybackend.users.RecodySignInSession;

public interface AppleLoginHandler<T> {

    RecodySignInSession handle(T t);
}
