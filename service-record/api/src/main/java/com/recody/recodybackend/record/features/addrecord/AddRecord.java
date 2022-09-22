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
    private String note;
    
    @Override
    public String toString() {
        return "{\"AddRecord\":{" + "\"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + "}}";
    }
}
