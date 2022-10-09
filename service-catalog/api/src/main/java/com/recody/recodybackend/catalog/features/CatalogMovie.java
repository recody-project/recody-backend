package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Content;
import lombok.Data;

@Data
public class CatalogMovie implements Content {
    
    private String contentId;
    
    /**
     * 이 카테고리는 사용자 지정 카테고리로 설정될 수 있어야 한다. */
    private Category category;
    private String title;
    private String imageUrl;
    
    @Override
    public String toString() {
        return "{\"CatalogMovie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentName\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null) + "}}";
    }
}
