package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.features.resolvecontentroot.ResolveContentRootResult;
import com.recody.recodybackend.movie.general.MovieGenre;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface MovieSearchMapper {
    
    SearchMovieResponse map(Object object);
    DynamicMapper dynamicMapper(Object object);
    
    interface DynamicMapper{
        DynamicMapper requestedLanguage(Locale locale);
        DynamicMapper genreIds(Map<Integer, List<MovieGenre>> movieGenres);
        DynamicMapper contentRoot(ResolveContentRootResult rootIdResult);
        SearchMovieResponse get();
    }
}
