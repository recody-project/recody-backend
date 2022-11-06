package com.recody.recodybackend.record.features.totalrecords;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountTotalRecords {
    
    private Long userId;
    private boolean thisMonth;
    
    @Override
    public String toString() {
        return "{\"CountTotalRecords\":{"
               + "\"userId\":" + userId
               + ", \"thisMonth\":" + thisMonth
               + "}}";
    }
}
