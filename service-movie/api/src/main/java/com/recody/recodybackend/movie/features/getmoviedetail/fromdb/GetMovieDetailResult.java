package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetMovieDetailResult {
    
    private GetMovieDetail requestInfo;
    private MovieSource source;
    private MovieDetail detail;
    
    public MovieSource getSource() { return MovieSource.TMDB; }
}
