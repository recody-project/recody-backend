package com.recody.recodybackend.record.features;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeleteRecordResponse {
    
    private LocalDateTime deletedAt;
    
}
