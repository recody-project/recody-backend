package com.recody.recodybackend.catalog.features.content.getdetail;

import com.recody.recodybackend.content.PersonalizedContentDetail;

/**
 * 작품 정보를 가져와 개인화한 후 반환한다.
 * 요청 받는 작품의 카테고리에 따라 서로다른 핸들러를 사용하여 작품정보를 가져온다.
 * 가져온 작품정보는 개인화하여 반환한다. */
@Deprecated
public interface GetContentDetailHandler {
    
    PersonalizedContentDetail handle(GetContentDetail command);
    
}
