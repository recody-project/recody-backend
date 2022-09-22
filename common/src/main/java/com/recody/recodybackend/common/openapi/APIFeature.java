package com.recody.recodybackend.common.openapi;

/*
 * 이 인터페이스는 APIRequest 로 변환할 수 있는 요청 객체이다.
 * APIRequest 가 가지는 toEntity 등을 비롯한 많은 메서드들을 매번 직접 구현하기에는 번거로움이 많다.
 * (toEntity, addHeader, addRequestParam, setApiKey, body)
 * 이 인터페이스의 주요 목적은 API 요청과 관련된 세부사항을 숨기고 요청을 간단하게 표현하기 위한 것이다.
 *
 * 또한 API 요청 특성상 base URI, path 등을 세팅하는 과정에서 깊은 상속이 생기게 된다.
 * 예를 들어서 http://api.google.com/ 이라는 base uri 를 가지는 APIRequest 객체가 있다고 해보자
 * 이때 다양한 api path 로 요청을 보내는 각각의 객체들을 만들고 싶다면 이 APIRequest 구현체를 여러 형태로 다시 상속해야한다.
 * (각 api path 로 요청을 보낼 때 argument 들이 다양할 수도 있기 때문에 서로 다른 path 를 사용하는
 * 여러 메서드를 구현하는 방식은 지양했다) -> 이런식으로 하다보면 하나의 클래스가 지나치게 많은 기능을 수행하게된다.
 *
 * 깊은 상속을 피하고 합성의 원리를 사용하기 위해
 * 이 인터페이스를 구현하여 간단하게 APIRequest 를 사용할 수 있게 한다.
 * 이 인터페이스를 구현하는 구현체는 APIRequest 구현체를 인스턴스화하고,
 * 원하는 path, argument 들을 세팅한 후 반환하면 된다.
 *
 * 따라서 이 요청 객체는 toAPIRequest() 외에는 모두 비즈니스 로직과 관련된 기능만 담는다.
 *
 * 여러 종류의 요청들이 AbstractRequestEntity 를 직접 상속하는 것은 복잡도를 올리기 때문에
 * 이 인터페이스를 대신 상속한다.
 *
 * api 요청과 관련된 공통 사항들은 모두
 * - AbstractRequestEntity
 * - ApiRequester
 * 가 처리한다.
 * */
public interface APIFeature {
    APIRequest toAPIRequest();
}
