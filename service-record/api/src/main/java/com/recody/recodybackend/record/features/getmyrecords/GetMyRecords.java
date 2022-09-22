package com.recody.recodybackend.record.features.getmyrecords;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetMyRecords {
    
    private Long userId;
    
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;
    
    @Override
    public String toString() {
        return "{\"GetMyRecords\":{" + "\"userId\":" + userId + ", \"page\":" + page + ", \"size\":" + size + "}}";
    }
}
