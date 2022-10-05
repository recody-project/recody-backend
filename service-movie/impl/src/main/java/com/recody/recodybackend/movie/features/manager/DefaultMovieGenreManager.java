package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreMapper;
import com.recody.recodybackend.movie.data.genre.MovieGenreRepository;
import com.recody.recodybackend.movie.exceptions.UnsupportedMovieSourceException;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieGenreManager implements MovieGenreManager {
    
    private final MovieGenreRepository repository;
    private final MovieGenreMapper mapper;
    
    @Override
    @Transactional
    public String register(MovieGenre genre) {
        Optional<String> optionalGenreId = findGenreId(genre);
        if (optionalGenreId.isPresent()) return optionalGenreId.get();
        
        // 장르 저장하기.
        MovieGenreEntity movieGenreEntity = mapper.map(genre);
        MovieGenreEntity savedGenre = repository.save(movieGenreEntity);
        String genreId = savedGenre.getGenreId();
        log.info("Recognized Movie Genre: genreId: {}", genreId);
    
        return genreId;
    }
    
    private Optional<String> findGenreId(MovieGenre genre) {
        Optional<MovieGenreEntity> optionalGenre;
        if (MovieSource.TMDB.equals(genre.getSource())){
            optionalGenre = repository.findByTmdbGenreId(genre.getGenreId());
            if (optionalGenre.isPresent()){
                return Optional.of(optionalGenre.get().getGenreId());
            }
        } else {
            throw new UnsupportedMovieSourceException();
        }
        return Optional.empty();
    }
}
