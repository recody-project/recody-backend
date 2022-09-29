package com.recody.recodybackend.movie.features.getmoviedetail;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TMDBMovieDetail {
    private Integer id;
    @JsonAlias(value = {"imdb_id"})
    private String imdbId;
    @JsonAlias(value = {"original_language"})
    private String originalLanguage;
    @JsonAlias(value = {"original_title"})
    private String originalTitle;
    private String overview;
    private List<TMDBMovieGenre> genres;
    private Float popularity;
    @JsonAlias(value = {"poster_path"})
    private String posterPath;
    @JsonAlias(value = {"production_countries"})
    private List<ProductionCountry> productionCountries;
    @JsonAlias(value = {"release_date"})
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    @JsonAlias(value = {"spoken_languages"})
    private List<SpokenLanguage> spokenLanguages;
    private String status;
    private String title;
    @JsonAlias(value = {"vote_average"})
    private Float voteAverage;
    @JsonAlias(value = {"vote_count"})
    private Integer voteCount;
    
//    @JsonIgnore
//    private MovieSource source = MovieSource.TMDB;
}
