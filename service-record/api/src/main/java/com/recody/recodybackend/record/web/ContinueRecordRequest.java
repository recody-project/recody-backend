package com.recody.recodybackend.record.web;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ContinueRecordRequest {
    
    @NotNull
    private String recordId;
    private String note;
    private String title;
}
