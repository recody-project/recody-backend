package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.productioncountry.*;
import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageRepository;
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
    private final MovieGenreCodeManager genreCodeManager;
    private final LanguageManager languageManager;
    private final ProductionCountryRepository productionCountryRepository;
    private final SpokenLanguageRepository spokenLanguageRepository;
    
    private final MovieGenreRepository movieGenreRepository;
    
    
    @Override
    @Transactional
    public String register(Movie movie) {
        Optional<String> optionalId = findMovieId(movie);
        if (optionalId.isPresent()) {
            return optionalId.get();
        }
        
        MovieEntity movieEntity = movieEntityMapper.toEntity(movie);
        log.debug("movieEntity: {}", movieEntity);
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        
    
        // 제작 국가를 영화에 등록하여 저장한다.
        List<ProductionCountry> productionCountries = movie.getProductionCountries();
        for (ProductionCountry productionCountry : productionCountries) {
            log.debug("productionCountry: {}", productionCountry);
            CountryEntity savedCountryEntity = countryManager.register(productionCountry);
            saveProductionCountry(savedMovie, savedCountryEntity);
        }
    
        List<MovieGenre> genres = movie.getGenres();
        for (MovieGenre genre : genres) {
            log.debug("genre: {}", genre);
            MovieGenreCodeEntity savedGenreCodeEntity = genreCodeManager.register(genre);
            saveMovieGenre(movieEntity, savedGenreCodeEntity);
        }
    
        List<SpokenLanguage> spokenLanguages = movie.getSpokenLanguages();
        for (SpokenLanguage spokenLanguage : spokenLanguages) {
            log.debug("spokenLanguage: {}", spokenLanguage);
            LanguageEntity languageEntity = languageManager.register(spokenLanguage);
            saveSpokenLanguage(movieEntity, languageEntity);
        }
        
        return savedMovie.getId();
    }
    
    private void saveSpokenLanguage(MovieEntity movieEntity, LanguageEntity languageEntity) {
        SpokenLanguageEntity spokenLanguageEntity = SpokenLanguageEntity.builder()
                                                         .language(languageEntity)
                                                         .movie(movieEntity)
                                                         .build();
        spokenLanguageRepository.save(spokenLanguageEntity);
    }
    
    private void saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode) {
        MovieGenreEntity movieGenreEntity = MovieGenreEntity.builder().genre(genreCode).movie(movieEntity).build();
        movieGenreRepository.save(movieGenreEntity);
    }
    
    private void saveProductionCountry(MovieEntity savedMovie, CountryEntity savedCountry) {
        ProductionCountryEntity productionCountryEntity = ProductionCountryEntity.builder()
                                                               .movie(savedMovie)
                                                               .country(savedCountry)
                                                               .build();
        productionCountryRepository.save(productionCountryEntity);
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
