package com.recody.recodybackend.catalog.features.record.deleterecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteRecord {
    
    private String recordId;
    private Long userId;
    
}
