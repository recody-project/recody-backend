package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.movie.features.getmoviedetail.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Movie implements Content {
    private String movieId;
    private Integer tmdbId;
    
    
    
    
    
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
    private Integer revenue;
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private Float voteAverage;
    private Integer voteCount;
    
    
    
    
    /* 장르와 root Id 는 외부에서 resolve 한 뒤에 세팅된다. */
    @Setter(AccessLevel.PUBLIC)
    private String posterPath;
    @Setter(AccessLevel.PUBLIC)
    private List<MovieGenre> genres;
    @Setter(AccessLevel.PUBLIC)
    private Integer rootId;
    
    @Override
    public Category getCategory() {
        return Category.Movie;
    }
    
    @Override
    public String toString() {
        return "{\"Movie\":{" + "\"movieId\":" + ((movieId != null) ? ("\"" + movieId + "\"") : null) + ", \"tmdbId\":" + tmdbId + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"popularity\":" + popularity + ", \"productionCountries\":" + productionCountries + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"genres\":" + genres + ", \"rootId\":" + rootId + "}}";
    }
}
