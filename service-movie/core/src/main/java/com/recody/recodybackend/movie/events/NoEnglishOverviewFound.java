package com.recody.recodybackend.movie.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoEnglishOverviewFound {
    
    private Long overviewId;
    
    @Override
    public String toString() {
        return "{\"NoEnglishOverviewFound\":{"
               + "\"overviewId\":" + overviewId
               + "}}";
    }
}
