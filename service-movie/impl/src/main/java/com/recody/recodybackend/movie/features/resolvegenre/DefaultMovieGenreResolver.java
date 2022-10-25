package com.recody.recodybackend.movie.features.resolvegenre;

import com.recody.recodybackend.common.contents.movie.MovieGenre;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeRepository;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.exceptions.GenreNotFoundException;
import com.recody.recodybackend.movie.exceptions.MovieErrorType;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeManager;
import com.recody.recodybackend.movie.features.resolvegenres.MovieGenreResolver;
import com.recody.recodybackend.movie.features.resolvegenre.fromapi.GetTMDBMovieGenresHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieGenreResolver implements MovieGenreResolver {
    
    private final ConcurrentMap<Integer, TMDBMovieGenre> tmdbGenreMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, MovieGenre> genreMap = new ConcurrentHashMap<>();
    private final MovieGenreCodeManager movieGenreCodeManager;
    private final GetTMDBMovieGenresHandler getTMDBMovieGenresHandler;
    private final MovieGenreCodeRepository genreCodeRepository;
    private final MovieGenreMapper genreMapper;
    
    @Override
    public TMDBMovieGenre toTMDBGenre(Integer tmdbGenreId) {
        log.debug("resolving tmdbGenreId: {}", tmdbGenreId);
        TMDBMovieGenre tmdbMovieGenre = tmdbGenreMap.get(tmdbGenreId);
        if (tmdbMovieGenre != null) {
            return tmdbMovieGenre;
        }
        
        log.warn("장르를 Map 에서 찾지못했습니다. 받은 tmdbGenreId: {}, TMDB 에서 장르를 다시 가져옵니다.", tmdbGenreId);
        List<TMDBMovieGenre> tmdbMovieGenres = getTMDBMovieGenresHandler.getTMDBMovieGenres();
        for (TMDBMovieGenre movieGenre : tmdbMovieGenres) {
            if (movieGenre.getId().equals(tmdbGenreId)) {
                return movieGenre;
            }
        }
        throw new GenreNotFoundException();
    }
    
    @Override
    public List<TMDBMovieGenre> toTMDBGenres(List<Integer> tmdbGenreId) {
        ArrayList<TMDBMovieGenre> tmdbMovieGenres = new ArrayList<>();
        for (Integer integer : tmdbGenreId) {
            TMDBMovieGenre tmdbMovieGenre = tmdbGenreMap.get(integer);
            tmdbMovieGenres.add(tmdbMovieGenre);
        }
        return tmdbMovieGenres;
    }
    
    @Override
    public MovieGenre toMovieGenre(Integer tmdbGenreId) {
        
        log.debug("to MovieGenre, tmdbGenreId: {}", tmdbGenreId);
        Optional<MovieGenreCodeEntity> optionalEntity = genreCodeRepository.findByTmdbGenreId(tmdbGenreId);
        if (optionalEntity.isEmpty()) {
            throw new GenreNotFoundException();
        }
        MovieGenreCodeEntity entity = optionalEntity.get();
        MovieGenre genre = genreMapper.map(entity);
        log.debug("mappedMovieGenre: {}", genre);
        return genre;
    }
    
    @Override
    public List<MovieGenre> toMovieGenreList(List<Integer> tmdbGenreIds) {
        ArrayList<MovieGenre> movieGenres = new ArrayList<>();
        for (Integer tmdbGenreId : tmdbGenreIds) {
            MovieGenre movieGenre = toMovieGenre(tmdbGenreId);
            movieGenres.add(movieGenre);
        }
        return movieGenres;
    }
    
    @Override
    public MovieGenre toMovieGenre(String genreId) {
        MovieGenre movieGenre = genreMap.get(genreId);
        if (movieGenre != null) {
            return movieGenre;
        }
        // db 에서 찾는다.
        Optional<MovieGenreCodeEntity> optionalEntity = genreCodeRepository.findById(genreId);
        if (optionalEntity.isEmpty()) {
            throw new ApplicationException(MovieErrorType.GenreNotFound, HttpStatus.NOT_FOUND, "영화 장르를 찾지 못했습니다.");
        }
        
        MovieGenreCodeEntity entity = optionalEntity.get();
        MovieGenre mappedGenre = genreMapper.map(entity);
        log.debug("mapped MovieGenre: {}", mappedGenre);
        return mappedGenre;
    }
    
    @Override
    @PostConstruct
    public void initTMDBGenres() {
        List<TMDBMovieGenre> tmdbMovieGenres = getTMDBMovieGenresHandler.getTMDBMovieGenres();
        for (TMDBMovieGenre tmdbMovieGenre : tmdbMovieGenres) {
            tmdbGenreMap.put(tmdbMovieGenre.getId(), tmdbMovieGenre);
        }
        log.info("{} 개의 영화 장르를 가져왔습니다.", tmdbMovieGenres.size());
        
        // 가지고 온다음 저장.
        List<MovieGenreCodeEntity> entities = movieGenreCodeManager.register(tmdbMovieGenres);
        for (MovieGenreCodeEntity entity : entities) {
            MovieGenre movieGenre = genreMapper.map(entity);
            genreMap.put(movieGenre.getGenreId(), movieGenre);
        }
    }
}
