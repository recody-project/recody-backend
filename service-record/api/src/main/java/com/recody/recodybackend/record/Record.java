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
    
    private String title;
    private String note;
    private boolean completed;
    
    @Override
    public String toString() {
        return "{\"Record\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + ", \"completed\":" + completed + "}}";
    }
}
