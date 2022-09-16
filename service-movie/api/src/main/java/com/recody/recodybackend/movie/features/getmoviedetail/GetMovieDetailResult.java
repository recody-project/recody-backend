package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.general.MovieSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMovieDetailResult {
    
    private GetMovieDetail requestInfo;
    private MovieSource source;
    private TMDBMovieDetail detail;
    
    public MovieSource getSource() { return MovieSource.TMDB; }
}
