package com.recody.recodybackend.movie.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoKoreanOverviewFound {
    
    private Long overviewId;
    
    @Override
    public String toString() {
        return "{\"NoKoreanOverviewFound\":{"
               + "\"overviewId\":" + overviewId
               + "}}";
    }
}
