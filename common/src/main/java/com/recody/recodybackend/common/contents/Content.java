package com.recody.recodybackend.common.contents;

/**
 * 작품을 의미한다. 모든 작품은 특정 카테고리에 속한다.
 * Content 는 외부 소스나 데이터베이스에 저장된 정보를 애플리케이션에서 객체화한 것이다.
 * 작품 정보의 내용은 달라질 수 있지만 유저가 조작한 정보는 포함되어서는 안된다. */
public interface Content {
    
    String getContentId();
    Category getCategory();

    
}
