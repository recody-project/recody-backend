package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.exceptions.UnsupportedMovieSourceException;
import com.recody.recodybackend.movie.features.getmoviedetail.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieRecognizer implements MovieRecognizer{
    
    private final MovieRepository movieRepository;
    private final MovieEntityMapper movieEntityMapper;
    private final ProductionCountryRecognizer productionCountryRecognizer;
    private final MovieGenreRecognizer genreRecognizer;
    private final SpokenLanguageRecognizer spokenLanguageRecognizer;
    
    
    
    @Override
    @Transactional
    public String recognize(Movie movie) {
        Optional<String> optionalId = findMovieId(movie);
        if (optionalId.isPresent()) {
            return optionalId.get();
        }
//        recognizeInfos(movie);
        MovieEntity movieEntity = movieEntityMapper.toEntity(movie);
        log.debug("movieEntity: {}", movieEntity);
        MovieEntity savedEntity = movieRepository.save(movieEntity);
        return savedEntity.getId();
    }
    
    private void recognizeInfos(Movie movie) {
        List<ProductionCountry> productionCountries = movie.getProductionCountries();
        for (ProductionCountry productionCountry : productionCountries) {
            productionCountryRecognizer.recognize(productionCountry);
        }
        List<MovieGenre> genres = movie.getGenres();
        for (MovieGenre genre : genres) {
            genreRecognizer.recognize(genre);
        }
        List<SpokenLanguage> spokenLanguages = movie.getSpokenLanguages();
        for (SpokenLanguage spokenLanguage : spokenLanguages) {
            spokenLanguageRecognizer.recognize(spokenLanguage);
        }
    }
    
    private Optional<String> findMovieId(Movie movie) {
        Optional<MovieEntity> optionalMovie;
        if (MovieSource.TMDB.equals(movie.getSource())){
            optionalMovie = movieRepository.findByTmdbId(movie.getId());
            if (optionalMovie.isPresent()){
                return Optional.of(optionalMovie.get().getId());
            }
        } else {
            throw new UnsupportedMovieSourceException();
        }
        return Optional.empty();
    }
}
