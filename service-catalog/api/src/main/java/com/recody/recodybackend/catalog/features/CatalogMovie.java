package com.recody.recodybackend.catalog.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.catalog.features.manager.CatalogContent;
import com.recody.recodybackend.common.contents.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogMovie implements CatalogContent {
    
    @JsonIgnore // 노출하지 않음.
    private String globalContentId;
    
    @JsonIgnore // 구현되지 않음.
    private String contentGroupId;
    private String contentId;
    /**
     * 이 카테고리는 사용자 지정 카테고리로 설정될 수 있어야 한다. */
    private Category category;
    private String title;
    private String posterPath;
    
    @Override
    public String toString() {
        return "{\"CatalogMovie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentName\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"imageUrl\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + "}}";
    }
}
