package com.recody.recodybackend.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* 유저에게 노출되는 데이터 클래스임을 명시한다.
* 이 어노테이션이 붙은 클래스의 필드는 원시타입 또는 값 래퍼 클래스 등 노출 가능한 타입이어야 한다.
* String, Integer, UUID, ... */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UserBoundDataClass {

}
