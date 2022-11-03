package com.recody.recodybackend.movie;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieInfo extends Movie {
    
    private Integer tmdbId;
    private MovieSource source;
    private String originalLanguage;
    
    private String originalTitle;
    private String overview;
    private String releaseDate;
    
    /* Details */
    private Float popularity;
    private Integer runtime;
    private Long revenue;
    private String status;
    private Float voteAverage;
    private Integer voteCount;
    private List<MovieGenre> genres;
    
    @Override
    public String toString() {
        return "[{\"MovieInfo\":{" + "\"tmdbId\":" + tmdbId + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"popularity\":" + popularity + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"genres\":" + genres + "}}, " + super.toString() + "]";
    }
}
