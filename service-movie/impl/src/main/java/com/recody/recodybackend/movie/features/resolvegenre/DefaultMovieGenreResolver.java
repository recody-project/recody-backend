package com.recody.recodybackend.movie.features.resolvegenre;

import com.recody.recodybackend.movie.features.resolvegenres.fromapi.GetMovieGenreFromTMDBApi;
import com.recody.recodybackend.movie.features.resolvegenres.fromdatabase.GetMovieGenreFromDataBase;
import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenres.ResolveMovieGenres;
import com.recody.recodybackend.movie.general.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
class DefaultMovieGenreResolver implements MovieGenreResolver {
    
    private final GetMovieGenreFromTMDBApi fromApi;
    private final GetMovieGenreFromDataBase fromDataBase;
    
    @Override
    public Map<Integer, List<MovieGenre>> handle(ResolveMovieGenres command) {
        Map<Integer, List<Integer>> genreIdsMap = command.getGenreIds();
        Map<Integer, List<MovieGenre>> resultMap = new HashMap<>();
    
        // 주어진 GenreIdsMap 에서 순서대로 genreId 들에 대해 MovieGenre 를 만들어
        // resultMap 에 세팅한ㄷ다.
        for (Integer key : genreIdsMap.keySet()) {
            List<Integer> genreIds = genreIdsMap.get(key);
            ArrayList<MovieGenre> genreList = new ArrayList<>();
            for (Integer genreId : genreIds) {
                if (fromApi.hasGenres()) {
                    MovieGenre movieGenre = fromApi.getMovieGenre(genreId);
                    log.debug("movieGenre: {}", movieGenre);
                    genreList.add(movieGenre);
                }
            }
            resultMap.put(key, genreList);
        }
        log.info("Resolved Genres: {}", resultMap);
        return resultMap;
    }
    
}
