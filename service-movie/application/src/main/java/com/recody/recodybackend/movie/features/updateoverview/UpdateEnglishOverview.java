package com.recody.recodybackend.movie.features.updateoverview;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateEnglishOverview {
    
    private Integer tmdbMovieId;
    
    @Override
    public String toString() {
        return "{\"UpdateEnglishOverview\":{"
               + "\"tmdbMovieId\":" + tmdbMovieId
               + "}}";
    }
}
