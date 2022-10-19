package com.recody.recodybackend.catalog.features.personalize;

import com.recody.recodybackend.catalog.PersonalizedContentDetail;
import com.recody.recodybackend.common.contents.BasicContentDetail;

/**
 * Content 객체의 내용을 유저가 수정한 내용에 따라 개인화된 내용으로 바꾼다.
 * 대표적으로 Genre, Category 를 수정한다.
 * Content 는 고유의 ContentId 를 가지고 있어야 한다.
 * ContentPersonalizer 는 하나의 userId 를 주입받아야 한다.
 *
 * 각 카테고리마다 서로다른 Personalizer 를 구현한다. */
public interface ContentDetailPersonalizer<T extends BasicContentDetail, P extends PersonalizedContentDetail> {
    
    P personalize(T content, Long userId);
}
