package com.recody.recodybackend.catalog.features.record.totalrecords;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountTotalRecords {
    
    private Long userId;
    
    private Monthly monthly;
    
    @Override
    public String toString() {
        return "{\"CountTotalRecords\":{"
               + "\"userId\":" + userId
               + ", \"yearMonth\":" + monthly
               + "}}";
    }
}
