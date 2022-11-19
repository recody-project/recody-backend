package com.recody.recodybackend.record.web;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.recody.recodybackend.record.RecordCount;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CountTotalRecordsResponse {
    
    @JsonUnwrapped
    private RecordCount count;
    
}
