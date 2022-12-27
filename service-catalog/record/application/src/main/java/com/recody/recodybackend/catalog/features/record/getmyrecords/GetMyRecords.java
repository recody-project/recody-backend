package com.recody.recodybackend.catalog.features.record.getmyrecords;

import com.recody.recodybackend.common.contents.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetMyRecords {
    
    private Long userId;
    
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;
    
    private Category category;
    
    private String sortingColumn;
    
    @Builder.Default
    private boolean descending = true;
    
    private String contentId;
    
    @Override
    public String toString() {
        return "{\"GetMyRecords\":{" + "\"userId\":" + userId + ", \"page\":" + page + ", \"size\":" + size + "}}";
    }
}
