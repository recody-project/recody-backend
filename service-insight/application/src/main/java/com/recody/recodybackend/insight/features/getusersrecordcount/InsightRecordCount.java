package com.recody.recodybackend.insight.features.getusersrecordcount;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InsightRecordCount {
    
    private Integer recordCount;
    
    public InsightRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }
    
    public Integer getRecordCount() {
        return recordCount;
    }
    
    @Override
    public String toString() {
        return "{\"InsightRecordCount\":{"
               + "\"recordCount\":" + recordCount
               + "}}";
    }
}
