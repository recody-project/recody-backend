package com.recody.recodybackend.record.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompleteRecordRequest {
    
    private String recordId;
    private String title;
    private String note;
}
