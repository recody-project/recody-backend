package com.recody.recodybackend.movie.features.searchmovies.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class TMDBMovieSearchNode {
    
    private boolean adult;
    @JsonAlias("backdrop_path")
    private String backdropPath;
    @JsonAlias("genre_ids")
    private List<Integer> genreIds;
    private Integer id;
    @JsonAlias("original_language")
    private String originalLanguage;
    @JsonAlias("original_title")
    private String originalTitle;
    private String overview;
    private Float popularity;
    @JsonAlias("poster_path")
    private String posterPath;
    @JsonAlias("release_date")
    private String releaseDate;
    private String title;
    private boolean video;
    @JsonAlias("vote_average")
    private Float voteAverage;
    
    @JsonAlias("vote_count")
    private Integer voteCount;
    
    
    @Override
    public String toString() {
        return "{\"TMDBMovieSearchNode\":{" + "\"adult\":" + adult + ", \"backdropPath\":" + ((backdropPath != null) ? ("\"" + backdropPath + "\"") : null) + ", \"genreIds\":" + genreIds + ", \"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"popularity\":" + popularity + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"video\":" + video + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + "}}";
    }
}
