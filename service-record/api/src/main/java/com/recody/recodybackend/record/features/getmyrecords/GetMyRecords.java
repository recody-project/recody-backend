package com.recody.recodybackend.record.features.getmyrecords;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetMyRecords {
    
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"GetMyRecords\":{" + "\"userId\":" + userId + "}}";
    }
}
