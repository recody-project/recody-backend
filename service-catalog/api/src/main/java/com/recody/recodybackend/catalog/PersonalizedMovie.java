package com.recody.recodybackend.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PersonalizedMovie implements PersonalizedContent{
    
    private Long personalizedUserId;
    private String contentId;
    private Integer tmdbId;
    
    private Category category = Category.Movie;
    
    // movie detail
    private String originalLanguage;
    private String title;
    private String originalTitle;
    
    private String overview;
    private Float popularity;
    private String posterPath;
    private List<MovieGenre> genres;
    
    private List<ProductionCountry> productionCountries;
    private List<SpokenLanguage> spokenLanguages;
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    private String status;
    private Float voteAverage;
    private Integer voteCount;
    
    @Override
    public Category getCategory() {
        return category;
    }
    
    @Override
    public Long getPersonalizedUserId() {
        return personalizedUserId;
    }
    
    @Override
    public String getContentId() {
        return contentId;
    }
}
