package com.recody.recodybackend.common.openapi.annotation;

import com.recody.recodybackend.common.openapi.AuthType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AuthenticateWith {
    
    AuthType type();
}
