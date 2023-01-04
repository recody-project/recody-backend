package com.recody.recodybackend.drama.features.synchronizedramaseachlanguages;

import com.recody.recodybackend.drama.SearchingKeyword;

/**
 * 특정 키워드로 검색한 결과를 영어, 한국어로 저장한다.
 * @author motive
 */
public interface SynchronizeDramasEachLanguagesHandler<R> {
    
    R handle(SearchingKeyword keyword);
    
}
