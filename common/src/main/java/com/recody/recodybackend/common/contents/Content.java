package com.recody.recodybackend.common.contents;

/**
 * 작품을 의미한다. 모든 작품은 특정 카테고리에 속한다.
 * Content 는 외부 소스나 데이터베이스에 저장된 정보를 애플리케이션에서 객체화한 것이다.
 * @param <T> 작품의 ID 타입
 * */
public interface Content<T> {
    
    T getContentId();
    Category getCategory();
    
}
