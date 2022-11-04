package com.recody.recodybackend.movie.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieCreated {
    
    private String contentId;
    private String posterUrl;
    
    @Override
    public String toString() {
        return "{\"MovieCreated\":{"
               + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null)
               + ", \"posterUrl\":" + ((posterUrl != null) ? ("\"" + posterUrl + "\"") : null)
               + "}}";
    }
}
