package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.BasicContentDetail;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieDetail extends Movie implements BasicContentDetail {
    
    
    private Integer tmdbId;
    private Integer rootId;
    
    private MovieSource source;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    
    
    /* Details */
    private Float popularity;
    private List<ProductionCountry> productionCountries;
    private Integer runtime;
    private Long revenue;
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private Float voteAverage;
    private Integer voteCount;
    private List<MovieGenre> genres;
    
    private List<Actor> actors;
    
    private List<Director> directors;
    
    @Override
    public String toString() {
        return "[{\"MovieDetail\":{" + "\"tmdbId\":" + tmdbId + ", \"rootId\":" + rootId + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"popularity\":" + popularity + ", \"productionCountries\":" + productionCountries + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"genres\":" + genres + ", \"actors\":" + actors + ", \"directors\":" + directors + "}}, " + super.toString() + "]";
    }
}
