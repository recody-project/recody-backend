package com.recody.recodybackend.record.web;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddRecordRequest {
    
    @NotNull(message = "{notNull}")
    private String contentId;
    private String title;
    private String note;
    
}
