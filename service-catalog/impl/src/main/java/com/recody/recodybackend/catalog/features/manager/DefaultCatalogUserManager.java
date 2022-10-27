package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.features.CatalogUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCatalogUserManager implements CatalogUserManager{
    
    /* 현재 별다른 검증 없이 객체를 반환한다.
    * 유저 동기화 로직이 완성되면 수정한다.*/
    @Override
    public CatalogUser load(Long userId) {
        return new CatalogUser(userId);
    }
}
