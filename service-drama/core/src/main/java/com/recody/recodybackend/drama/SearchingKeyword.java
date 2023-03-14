package com.recody.recodybackend.drama;

import lombok.Getter;

/**
 * 검색어.
 * <li> 아직 별 다른 로직은 없습니다. </li>
 * <li> 키워드의 길이 등 제약조건을 구현할 수 있습니다.</li>
 * @author motive
 */
@Getter
public class SearchingKeyword {
    
    private final String value;
    
    public SearchingKeyword(String value) {
        this.value = value;
    }
    
    public static SearchingKeyword of(String keyword) {
        return new SearchingKeyword( keyword );
    }
    
    @Override
    public String toString() {
        return "{\"SearchingKeyword\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
