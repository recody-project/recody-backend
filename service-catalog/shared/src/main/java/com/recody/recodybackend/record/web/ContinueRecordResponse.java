package com.recody.recodybackend.record.web;

import com.recody.recodybackend.record.RecordEvent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContinueRecordResponse {
    
    private String recordId;
    private List<RecordEvent> events;
    
    @Override
    public String toString() {
        return "{\"ContinueRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"events\":" + events + "}}";
    }
}
