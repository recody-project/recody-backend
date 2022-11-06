package com.recody.recodybackend.record;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordContent {
    
    private String recordId;
    private String contentId;
    private String imageUrl;
    private String title;
    private LocalDate appreciationDate;
    
    private LocalDateTime lastModifiedAt;
    
    @Override
    public String toString() {
        return "{\"RecordContent\":{"
               + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null)
               + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null)
               + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null)
               + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null)
               + ", \"appreciationDate\":" + appreciationDate
               + ", \"lastModifiedAt\":" + ((lastModifiedAt != null) ? ("\"" + lastModifiedAt + "\"") : null)
               + "}}";
    }
}
