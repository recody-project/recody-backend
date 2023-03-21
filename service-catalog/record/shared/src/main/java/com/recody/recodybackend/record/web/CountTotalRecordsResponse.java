package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.RecordCount;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountTotalRecordsResponse {
    
    private RecordCount count;
    
}
