package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.RecordContent;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetContinuingRecordContentResponse {
    
    private RecordContent recordContent;
    
}
