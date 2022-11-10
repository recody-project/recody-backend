package com.recody.recodybackend.record;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    private LocalDate appreciationDate;
    
    @Override
    public String toString() {
        return "{\"Record\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + ", \"completed\":" + completed + ", \"createdAt\":" + ((createdAt != null) ? ("\"" + createdAt + "\"") : null) + ", \"lastModifiedAt\":" + ((lastModifiedAt != null) ? ("\"" + lastModifiedAt + "\"") : null) + ", \"appreciationDate\":" + appreciationDate + "}}";
    }
}
