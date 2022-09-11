package com.recody.recodybackend.movie.features.getmoviedetail;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TMDBMovieDetail {
    private Integer id;
    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private Float popularity;
    private String posterPath;
    private List<ProductionCountry> productionCountries;
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private String title;
    private Float voteAverage;
    private Integer voteCount;
}
