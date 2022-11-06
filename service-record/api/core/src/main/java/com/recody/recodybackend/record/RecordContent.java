package com.recody.recodybackend.record;

import lombok.*;

import java.time.LocalDate;

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
    
    @Override
    public String toString() {
        return "{\"RecordContent\":{"
               + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null)
               + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null)
               + ", \"imageUrl\":" + ((imageUrl != null) ? ("\"" + imageUrl + "\"") : null)
               + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null)
               + ", \"appreciationDate\":" + appreciationDate
               + "}}";
    }
}
