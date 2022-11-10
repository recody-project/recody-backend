package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchContentWithFilters {
    
    
    private String keyword;
    private String language;
    
    /**
     * 검색의 대상이 될 카테고리들입니다.
     */
    @Builder.Default
    private List<BasicCategory> categories = BasicCategory.all();
    
    @Override
    public String toString() {
        return "{\"SearchContentV2\":{"
               + "\"keyword\":" + ((keyword != null) ? ("\"" + keyword + "\"") : null)
               + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null)
               + ", \"categories\":" + categories
               + "}}";
    }
}
