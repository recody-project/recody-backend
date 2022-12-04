package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetailFetched {
    
    private TMDBMovieDetail tmdbMovieDetail;
    private Locale locale;
    private List<TMDBCast> casts;
    private List<TMDBCrew> crews;
    
    @Override
    public String toString() {
        return "\"MovieDetailFetched\"";
    }
}
