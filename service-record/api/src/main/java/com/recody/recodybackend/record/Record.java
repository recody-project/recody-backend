package com.recody.recodybackend.record;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class Record {
    
    private String recordId;
    @NonNull
    private Long userId;
    @NonNull
    private String contentId;
    private String note;
    
    @Override
    public String toString() {
        return "{\"Record\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + "}}";
    }
}
