package com.recody.recodybackend.movie.features.resolvegenre;

import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenres.ResolveMovieGenres;
import com.recody.recodybackend.movie.general.MovieGenre;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
class DefaultMovieGenreResolver implements MovieGenreResolver {
    
    @Override
    public Map<Integer, List<MovieGenre>> handle(ResolveMovieGenres command) {
        Map<Integer, List<Integer>> genreIds = command.getGenreIds();
        
        Map<Integer, List<MovieGenre>> map = new HashMap<>();
        
        // TODO: 임시 구현
        Set<Integer> keys = genreIds.keySet();
        for (Integer key : keys) {
            List<MovieGenre> list = new ArrayList<>();
            List<Integer> genreIdsFromMap = genreIds.get(key);
            for (Integer integer : genreIdsFromMap) {
                list.add(new MovieGenre(integer, "임시 장르"));
            }
            map.put(key, list);
        }
        return map;
    }
    
}
