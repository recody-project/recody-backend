package com.recody.recodybackend.movie.features.getmoviedetail.fromapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder(value = {"tmdbId"})
public class TMDBFetchedMovieDetail {
    
    @JsonProperty("tmdbId")
    private Integer contentId;
    @Builder.Default
    private BasicCategory category = BasicCategory.Movie;
    
    private String title;
    private String posterPath;
    
    private String overview;
    private String releaseDate;
    private Integer runtime;
    
    private List<TMDBMovieGenre> genres;
    private List<Actor> actors;
    private List<Director> directors;
    
}
