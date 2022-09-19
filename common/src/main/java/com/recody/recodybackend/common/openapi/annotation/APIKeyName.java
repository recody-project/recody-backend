package com.recody.recodybackend.common.openapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface APIKeyName {

    String name() default "";
}
