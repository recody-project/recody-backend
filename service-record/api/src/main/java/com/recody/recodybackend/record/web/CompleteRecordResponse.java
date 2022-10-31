package com.recody.recodybackend.record.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompleteRecordResponse {
    
    private String recordId;
    private boolean completed;
    
    @Override
    public String toString() {
        return "{\"CompleteRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"completed\":" + completed + "}}";
    }
}
