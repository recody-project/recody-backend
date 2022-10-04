package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.common.contents.Category;
import lombok.Data;

@Data
public class CatalogMovie implements CatalogContent {
    
    private String contentId;
    private Category category;
    private String title;
    private String imageUrl;
    
    @Override
    public String toString() {
        return "{\"CatalogMovie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"contentName\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null) + "}}";
    }
}
