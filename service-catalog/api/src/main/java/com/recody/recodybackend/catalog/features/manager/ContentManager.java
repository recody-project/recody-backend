package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.common.contents.Content;

/**
 * 작품 정보에 고유 ID 를 부여한다.
 * 이 ID 는 모든 카테고리의 작품들이 공유하는 식별자이다.
 */
public interface ContentManager {
    
    /**
     * 작품 도메인 객체를 주면 고유 ID를 반환한다.
     * 해당 작품은 Catalog 서비스에서 사용할 수 있는 객체로서 인식된다. */
    <T extends Content> String register(T content);
    
}
