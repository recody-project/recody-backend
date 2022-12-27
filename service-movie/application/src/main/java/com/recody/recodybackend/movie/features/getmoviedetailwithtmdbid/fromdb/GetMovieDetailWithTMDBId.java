package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMovieDetailWithTMDBId {
    
    private Integer tmdbId;
    private String language;
    
    @Override
    public String toString() {
        return "{\"GetMovieDetail\":{" + "\"tmdbId\":" + tmdbId + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null) + "}}";
    }
}
