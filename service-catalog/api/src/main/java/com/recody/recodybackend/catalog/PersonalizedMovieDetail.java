package com.recody.recodybackend.catalog;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.movie.features.getmoviedetail.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PersonalizedMovieDetail implements PersonalizedContent{
    private Integer id;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Float popularity;
    private String posterPath;
    private List<ProductionCountry> productionCountries;
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    private List<MovieGenre> genres;
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private String title;
    private Float voteAverage;
    private Integer voteCount;
    private Category category;
    
    @Override
    public Category getCategory() {
        return category;
    }
    
    @Override
    public Long getPersonalizedUserId() {
        return null;
    }
    
    @Override
    public String getContentId() {
        return null;
    }
    
    @Override
    public String toString() {
        return "{\"PersonalizedMovieDetail\":{" + "\"id\":" + id + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"popularity\":" + popularity + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"productionCountries\":" + productionCountries + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"genres\":" + genres + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + "}}";
    }
}
