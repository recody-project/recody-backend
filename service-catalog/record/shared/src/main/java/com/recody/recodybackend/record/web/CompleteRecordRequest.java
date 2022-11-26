package com.recody.recodybackend.record.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompleteRecordRequest {
    
    private String recordId;
    
}
