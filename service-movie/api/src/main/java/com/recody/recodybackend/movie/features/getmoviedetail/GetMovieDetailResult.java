package com.recody.recodybackend.movie.features.getmoviedetail;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetMovieDetailResult {
    
    private GetMovieDetail requestInfo;
    private MovieSource source;
    private TMDBMovieDetail detail;
    
    public MovieSource getSource() { return MovieSource.TMDB; }
}
