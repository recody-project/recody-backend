package com.recody.recodybackend.movie.features.resolvegenre.fromdatabase;

import com.recody.recodybackend.movie.data.MovieGenreEntity;
import com.recody.recodybackend.movie.data.MovieGenreRepository;
import com.recody.recodybackend.movie.features.resolvegenres.fromdatabase.GetMovieGenreFromDataBase;
import com.recody.recodybackend.movie.general.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class GetMovieGenreFromMyDatabaseHandler implements GetMovieGenreFromDataBase {
    
    private final MovieGenreRepository genreRepository;
    
    @Override
    public MovieGenre getMovieGenre(Integer tmdbGenreId) {
        Optional<MovieGenreEntity> optionalGenre = genreRepository.findByTmdbGenreId(tmdbGenreId);
        if (optionalGenre.isPresent()){
            String tmdbGenreName = optionalGenre.get().getTmdbGenreName();
            MovieGenre movieGenre = new MovieGenre(tmdbGenreId, tmdbGenreName);
            log.debug("found movieGenre from db: {}", movieGenre);
            return movieGenre;
        } else {
            return null;
        }
    }
}
