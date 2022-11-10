package com.recody.recodybackend.record.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddRecordResponse {
    private String recordId;
    
    @Override
    public String toString() {
        return "{\"AddRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + "}}";
    }
}
