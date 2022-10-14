package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.productioncountry.*;
import com.recody.recodybackend.movie.exceptions.UnsupportedMovieSourceException;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
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
class DefaultMovieManager implements MovieManager {
    
    private final MovieRepository movieRepository;
    private final MovieEntityMapper movieEntityMapper;
    private final CountryManager countryManager;
    private final MovieGenreManager genreRecognizer;
    private final SpokenLanguageManager spokenLanguageRecognizer;
    
    private final CountryRepository countryRepository;
    
    private final ProductionCountryMapper productionCountryMapper;
    
    private final ProductionCountryRepository productionCountryRepository;
    
    
    @Override
    @Transactional
    public String register(Movie movie) {
        Optional<String> optionalId = findMovieId(movie);
        if (optionalId.isPresent()) {
            return optionalId.get();
        }
        registerInfos(movie);
        MovieEntity movieEntity = movieEntityMapper.toEntity(movie);
        log.debug("movieEntity: {}", movieEntity);
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        
        List<ProductionCountry> productionCountries = movie.getProductionCountries();
        
        for (ProductionCountry productionCountry : productionCountries) {
            log.debug("productionCountry: {}", productionCountry);
            CountryEntity savedCountry = countryManager.register(productionCountry);
            ProductionCountryEntity productionCountryEntity = ProductionCountryEntity.builder()
                                                                   .movie(savedMovie)
                                                                   .country(savedCountry)
                                                                   .build();
            ProductionCountryEntity savedProductionCountry = productionCountryRepository.save(productionCountryEntity);
        }
        return savedMovie.getId();
    }
    
    private void registerInfos(Movie movie) {
        List<MovieGenre> genres = movie.getGenres();
        for (MovieGenre genre : genres) {
            log.debug("genre: {}", genre);
            String genreId = genreRecognizer.register(genre);
            log.debug("genreId: {}", genreId);
        }
        List<SpokenLanguage> spokenLanguages = movie.getSpokenLanguages();
        for (SpokenLanguage spokenLanguage : spokenLanguages) {
            log.debug("spokenLanguage: {}", spokenLanguage);
            spokenLanguageRecognizer.register(spokenLanguage);
        }
    }
    
    private Optional<String> findMovieId(Movie movie) {
        Optional<MovieEntity> optionalMovie;
        if (MovieSource.TMDB.equals(movie.getSource())){
            optionalMovie = movieRepository.findByTmdbId(movie.getTmdbId());
            if (optionalMovie.isPresent()){
                return Optional.of(optionalMovie.get().getId());
            }
        } else {
            throw new UnsupportedMovieSourceException();
        }
        return Optional.empty();
    }
}
