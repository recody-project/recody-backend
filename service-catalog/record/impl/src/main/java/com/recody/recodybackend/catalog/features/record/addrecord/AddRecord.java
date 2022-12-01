package com.recody.recodybackend.catalog.features.record.addrecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

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
    
    /**
     * 감상일
     * */
    private LocalDate appreciationDate;
    
    @Override
    public String toString() {
        return "{\"AddRecord\":{" + "\"userId\":" + userId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"note\":" + ((note != null) ? ("\"" + note + "\"") : null) + ", \"appreciationDate\":" + appreciationDate + "}}";
    }
}
