package com.recody.recodybackend.common.openapi;

/*
* 요청 객체.
* 이 요청 객체는 toEntity() 외에는 모두 비즈니스 로직과 관련된 기능만 담는다.
* 요청 entity 로 바꾸어서 api 요청에 사용할 수 있다.
* 여러 종류의 요청들이 AbstractRequestEntity 를 직접 상속하는 것은 복잡도를 올리기 때문에
* 이 인터페이스를 대신 상속한다.
*
* api 요청과 관련된 공통 사항들은 모두
* - AbstractRequestEntity
* - ApiRequester
* 가 처리한다. */
public interface Request<T> {
    T toEntity();
}
