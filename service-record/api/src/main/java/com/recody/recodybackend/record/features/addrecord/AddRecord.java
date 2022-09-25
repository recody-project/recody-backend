package com.recody.recodybackend.record.features.addrecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * 감상평을 등록한다. */
@Data
@Builder
public class AddRecord {
    
    @NonNull
    private Long userId;
    @NonNull
    private String contentId;
    private String title;
    private String note;
    
    @Override
    public String toString() {
        return "{\"AddRecord\":{" + "\"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + "}}";
    }
}
