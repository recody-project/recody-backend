package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.catalog.features.api.movie.MovieAPIRequest;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class DefaultSearchMoviesHandler implements SearchMoviesHandler{
    
    private final APIRequester<MovieAPIRequest> requester;
    
    @Override
    public SearchMoviesResult handle(SearchMovies command) {
        MovieSearchRequest request = new MovieSearchRequest(command.getKeyword(), command.getLanguage());
        SearchMoviesResult result = requester.requestAndGet(request, SearchMoviesResult.class);
        List<Movie> movies = result.getMovies();
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        return result;
    }
}
