package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeManager;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeRepository;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieGenreCodeManager implements MovieGenreCodeManager {
    
    private final MovieGenreCodeRepository repository;
    
    @Override
    @Transactional
    public MovieGenreCodeEntity register(TMDBMovieGenre tmdbMovieGenre) {
        Integer tmdbId = tmdbMovieGenre.getId();
        Optional<MovieGenreCodeEntity> optionalEntity = repository.findByTmdbGenreId(tmdbId);
        
        if (optionalEntity.isPresent()) {
            return optionalEntity.get();
        }
        
        MovieGenreCodeEntity entity = MovieGenreCodeEntity.builder()
                                                         .tmdbGenreId(tmdbId)
                                                         .tmdbGenreName(tmdbMovieGenre.getName())
                                                         .build();
        MovieGenreCodeEntity savedGenreEntity = repository.save(entity);
        log.debug("registered genre: {}", savedGenreEntity.getGenreId());
        return savedGenreEntity;
    }
    
    @Override
    public List<MovieGenreCodeEntity> register(List<TMDBMovieGenre> tmdbMovieGenres) {
        log.debug("TMDB 영화 장르 등록 중 : {} 개", tmdbMovieGenres.size());
        return MovieGenreCodeManager.super.register(tmdbMovieGenres);
    }
}
