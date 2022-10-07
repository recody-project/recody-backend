package com.recody.recodybackend.catalog;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.ContentDetail;

/**
 * 유저의 정보가 반영된 컨텐츠 정보.
 * 단 하나의 유저가 수정한 작품정보이다. */
public interface PersonalizedContentDetail extends ContentDetail {
    
    Category getCategory();
    Long getPersonalizedUserId();
    
    /**
     * 레코디에서 관리하는 고유 컨텐츠 Id를 가진다. */
    String getContentId();
}
