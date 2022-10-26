package com.recody.recodybackend.catalog.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.catalog.features.manager.CatalogContentDetail;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.MovieGenre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class CatalogMovieDetail implements CatalogContentDetail {
    
    @JsonIgnore // 노출하지 않음.
    private String globalContentId;
    
    @JsonIgnore // 구현되지 않음.
    private String contentGroupId;
    
    private String contentId;
    
    private BasicCategory category = BasicCategory.Movie;
    
    /* movie detail */
    private String posterPath;
    
    private String overview;
    private String title;
    private String originalTitle;
    private String releaseDate;
    private Integer runtime;
    private List<MovieGenre> genres;
    private List<Actor> actors;
    private List<Director> directors;
    
    
    
//    private Float popularity;
//    private List<ProductionCountry> productionCountries;
//    private List<SpokenLanguage> spokenLanguages;
//    private Long revenue;
//    private String status;
//    private Float voteAverage;
//    private Integer voteCount;

}
