package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.Record;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetContinuingRecordResponse {
    
    private Record record;
}
