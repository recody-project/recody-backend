package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.genre.*;
import com.recody.recodybackend.movie.general.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieGenreCodeManager implements MovieGenreCodeManager {
    
    private final MovieGenreCodeRepository repository;
    private final MovieGenreMapper mapper;
    
    @Override
    @Transactional
    public MovieGenreCodeEntity register(MovieGenre genre) {
        Integer genreId1 = genre.getGenreId();
        Optional<MovieGenreCodeEntity> optionalGenre = repository.findByTmdbGenreId(genreId1);
        
        if (optionalGenre.isPresent()) {
            return optionalGenre.get();
        }
        
        // 장르 저장하기.
        MovieGenreCodeEntity movieGenreEntity = mapper.map(genre);
        MovieGenreCodeEntity savedGenre = repository.save(movieGenreEntity);
        String genreId = savedGenre.getGenreId();
        log.info("Recognized Movie Genre: genreId: {}", genreId);
    
        return savedGenre;
    }
}
