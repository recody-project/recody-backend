package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import com.recody.recodybackend.common.contents.movie.MovieDetail;
import com.recody.recodybackend.common.contents.movie.MovieSource;
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
