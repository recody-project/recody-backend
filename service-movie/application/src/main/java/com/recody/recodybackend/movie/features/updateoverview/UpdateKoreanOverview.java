package com.recody.recodybackend.movie.features.updateoverview;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateKoreanOverview {
    
    private Integer tmdbMovieId;
    
    @Override
    public String toString() {
        return "{\"UpdateKoreanOverview\":{"
               + "\"tmdbMovieId\":" + tmdbMovieId
               + "}}";
    }
}
