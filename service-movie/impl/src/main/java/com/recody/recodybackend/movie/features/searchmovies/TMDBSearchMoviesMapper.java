package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
class TMDBSearchMoviesMapper implements SearchMoviesMapper {
    
    private static final String POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";
    
    public DynamicMapper apiResponse(SearchMoviesUsingApiResponse response){
        SearchMoviesResult result = map(response);
        return new TMDBDynamicMapper(result);
    }
    
    private SearchMoviesResult map(SearchMoviesUsingApiResponse response) {
        ArrayList<Movie> specs = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            specs.add(doMap(response, i));
        }
        return SearchMoviesResult.builder()
                                 .movies(specs).build();
    }
    
    private Movie doMap(SearchMoviesUsingApiResponse results, int n) {
        log.debug("mapping api results to movie");
        log.trace("results: {}", results);
        return Movie.builder()
                    .tmdbId(results.getMovieId(n))
                    .originalLanguage(results.getOriginalLanguage(n))
                    .originalTitle(results.getOriginalTitle(n))
                    .overview(results.getOverview(n))
                    .posterPath(POSTER_PATH_PREFIX.concat(results.getPosterPath(n)))
                    .releaseDate(results.getReleaseDate(n))
                    .title(results.getTitle(n))
                    .source(results.getContentSource()).build();
    }
}
