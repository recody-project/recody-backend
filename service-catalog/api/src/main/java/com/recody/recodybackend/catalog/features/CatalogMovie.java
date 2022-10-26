package com.recody.recodybackend.catalog.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.catalog.features.manager.CatalogContent;
import com.recody.recodybackend.common.contents.BasicCategory;
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
    
    private BasicCategory category;
    private String title;
    private String posterPath;
    
    @Override
    public String toString() {
        return "{\"CatalogMovie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentName\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"imageUrl\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + "}}";
    }
}
