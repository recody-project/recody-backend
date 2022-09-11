package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.movie.features.resolvecontentroot.ContentRootResolver;
import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRoot;
import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRootResult;
import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenres.ResolveMovieGenres;
import com.recody.recodybackend.movie.features.searchmovies.request.SearchMoviesUsingTMDBApi;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
class TMDBMovieSearchService implements MovieSearchService {
    
    private final SearchMoviesUsingApiHandler searchHandler;
    private final MovieGenreResolver genreResolver;
    private final SearchMoviesMapper searchMapper;
    private final ContentRootResolver rootResolver;
    
    @Override
    public SearchMoviesResult handle(SearchMovies command) {
        SearchMoviesUsingApiResponse response = searchHandler.handleToJson(SearchMoviesUsingTMDBApi
                                                                                    .builder()
                                                                                    .movieName(command.getMovieName())
                                                                                    .language(command.getLanguage())
                                                                                    .build());
        Locale locale = resolveLocale(command);
        
        Map<Integer, List<MovieGenre>> movieGenres = resolveGenres(response);
        
        ResolveContentRootResult contentRoot = resolveContentRoot(response);
        
        return searchMapper
                .apiResponse(response)
                .requestedLanguage(locale)
                .genreIds(movieGenres)
                .contentRoot(contentRoot)
                .get();
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
    
    private ResolveContentRootResult resolveContentRoot(SearchMoviesUsingApiResponse result) {
        return rootResolver.handle(ResolveContentRoot
                                           .builder()
                                           .category(Category.Movie)
                                           .contentSource(MovieSource.TMDB)
                                           .contentId(result.getMovieIds())
                                           .build());
    }
    
    private Map<Integer, List<MovieGenre>> resolveGenres(SearchMoviesUsingApiResponse result) {
        return genreResolver.handle(ResolveMovieGenres.builder().genreIds(result.getGenreIdsMap()).build());
    }
}
