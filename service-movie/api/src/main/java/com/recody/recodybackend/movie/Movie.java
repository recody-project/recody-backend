package com.recody.recodybackend.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.ContentDetail;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Movie implements ContentDetail {
    @JsonIgnore // 유저에게는 contentId 라는 이름으로 노출한다.
    private String movieId;
    private Integer tmdbId;
    private Integer rootId;
    @Builder.Default
    private BasicCategory category = BasicCategory.Movie;
    
    
    
    private MovieSource source;
    private String originalLanguage;
    private String originalTitle;
    private String title;
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
    private String posterPath;
    private List<MovieGenre> genres;
    
    private List<Actor> actors;
    
    private List<Director> directors;
    
    @Override
    public String getContentId() {
        return movieId;
    }
    
    @Override
    public BasicCategory getCategory() {
        return category;
    }
    
    @Override
    public String toString() {
        return "{\"Movie\":{" + "\"movieId\":" + ((movieId != null) ? ("\"" + movieId + "\"") : null) + ", \"tmdbId\":" + tmdbId + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"popularity\":" + popularity + ", \"productionCountries\":" + productionCountries + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"genres\":" + genres + ", \"rootId\":" + rootId + "}}";
    }
}
