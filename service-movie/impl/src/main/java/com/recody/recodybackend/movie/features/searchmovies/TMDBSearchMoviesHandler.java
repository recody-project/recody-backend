package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchResponse;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
class TMDBSearchMoviesHandler implements SearchMoviesHandler {
    private final APIRequester<TMDBAPIRequest> tmdbRequester;
    
    private final TMDBMovieSearchMapper movieSearchMapper;
    
    @Override
    public SearchMoviesResult handle(SearchMovies command) {
        TMDBMovieSearchResponse response = tmdbRequester.requestAndGet(TMDBSearchMoviesRequest
                                                                               .builder()
                                                                               .movieName(command.getMovieName())
                                                                               .language(command.getLanguage())
                                                                               .build(), TMDBMovieSearchResponse.class);
        Locale locale = resolveLocale(command);
        
        List<TMDBMovieSearchNode> tmdbMovies = response.getResults();
        List<Movie> movies = movieSearchMapper.mapList(tmdbMovies);
        
        return SearchMoviesResult.builder()
                                 .movies(movies)
                                 .requestedLanguage(locale)
                                 .build();
    }
    
    private Locale resolveLocale(SearchMovies command) {
        Locale locale;
        try {
            locale = new Locale(command.getLanguage());
        } catch (Exception exception) {
            throw new RuntimeException("language 파라미터가 올바르지 않습니다.");
        }
        return locale;
    }
}
