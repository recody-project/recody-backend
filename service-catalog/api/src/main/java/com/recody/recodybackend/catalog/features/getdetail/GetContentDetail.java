package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.common.contents.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetContentDetail {
    
    private String contentId;
    private String language;
    private Category category;
    private Long userId;

}
