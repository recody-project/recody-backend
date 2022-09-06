package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenres.ResolveMovieGenres;
import com.recody.recodybackend.movie.features.resolvecontentroot.ContentRootResolver;
import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRoot;
import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRootResult;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchResult;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchTemplate;
import com.recody.recodybackend.movie.features.searchmovies.request.TMDBMovieSearchRequestEntity;
import com.recody.recodybackend.movie.general.Category;
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
    
    private final MovieSearchTemplate searchTemplate;
    private final MovieGenreResolver genreResolver;
    private final MovieSearchMapper searchMapper;
    private final ContentRootResolver rootResolver;
    
    @Override
    public SearchMovieResponse handle(SearchMovie command) {
        MovieSearchResult result = searchTemplate.executeToJson(TMDBMovieSearchRequestEntity.builder().movieName(command.getMovieName()).language(command.getLanguage()).build());
        
        return doHandle(result);
    }
    
    private SearchMovieResponse doHandle(MovieSearchResult result) {
        // 장르 세팅
        Map<Integer, List<MovieGenre>> movieGenres = resolveGenres(result);
        
        // root id 세팅
        ResolveContentRootResult contentRoot = resolveContentRoot(result);
        
        // 요청 언어 세팅
        return searchMapper.dynamicMapper(result)
                           .requestedLanguage(result.getLocale())
                           .genreIds(movieGenres)
                           .contentRoot(contentRoot).get();
    }
    
    private ResolveContentRootResult resolveContentRoot(MovieSearchResult result) {
        return rootResolver.handle(ResolveContentRoot.builder()
                                                     .category(Category.Movie)
                                                     .contentSource(MovieSource.TMDB)
                                                     .contentId(result.getMovieIds()).build());
    }
    
    private Map<Integer, List<MovieGenre>> resolveGenres(MovieSearchResult result) {
        return genreResolver.handle(ResolveMovieGenres.builder()
                                                      .genreIds(result.getGenreIdsMap())
                                                      .build());
    }
}
