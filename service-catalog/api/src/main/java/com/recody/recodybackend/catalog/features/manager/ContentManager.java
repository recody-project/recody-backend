package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.common.contents.Content;

/**
 * Catalog 서비스에서 관리하는 컨텐츠로 등록한다. <br>
 * <br>
 * <b> Catalog 서비스의 컨텐츠의 특징 </b>
 * <ol>
 *     <li> 모든 컨텐츠가 공유하는 고유 id 를 갖는다. </li>
 *     <li> 컨텐츠 그룹을 갖는다. </li>
 * </ol>
 */
public interface ContentManager {
    
    /**
     * 작품 도메인 객체를 주면 고유 ID를 반환한다.
     * 해당 작품은 Catalog 서비스에서 사용할 수 있는 객체로서 인식된다. */
    <T extends Content<?>> String register(T content);
    
}
