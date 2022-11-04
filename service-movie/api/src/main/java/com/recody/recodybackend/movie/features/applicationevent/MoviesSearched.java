package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoviesSearched {
    
    private List<TMDBMovieSearchNode> tmdbMovies;
    private Locale locale;
    
    @Override
    public String toString() {
        return "\"MoviesSearched\"";
    }
}
