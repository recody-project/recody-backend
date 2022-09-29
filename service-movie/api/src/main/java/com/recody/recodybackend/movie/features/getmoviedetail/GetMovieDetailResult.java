package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetMovieDetailResult {
    
    private GetMovieDetail requestInfo;
    private MovieSource source;
    private Movie detail;
    
    public MovieSource getSource() { return MovieSource.TMDB; }
}
