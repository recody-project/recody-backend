package com.recody.recodybackend.book.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCreated {

    private String contentId;
    private String imagePath;
    private String title;

    @Override
    public String toString() {
        return "{\"BookCreated\":{"
                + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null)
                + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null)
                + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null)
                + "}}";
    }
}
