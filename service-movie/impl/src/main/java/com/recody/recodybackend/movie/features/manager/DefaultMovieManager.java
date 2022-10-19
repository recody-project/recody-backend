package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.data.MovieEntityManager;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.exceptions.UnsupportedMovieSourceException;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import com.recody.recodybackend.movie.features.getmoviecredit.Director;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
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
    private final MovieEntityManager movieEntityManager;
    
    private final PersonManager personManager;
    
    
    @Override
    @Transactional
    public String register(MovieDetail movieDetail, Locale locale) {
        log.debug("locale: {}", locale);
        Optional<MovieEntity> optionalMovie;
        String title = movieDetail.getTitle();
        if (!MovieSource.TMDB.equals(movieDetail.getSource())) {
            throw new UnsupportedMovieSourceException();
        }
        
        optionalMovie = movieRepository.findByTmdbId(movieDetail.getTmdbId());
        if (optionalMovie.isPresent()) {
            MovieEntity movieEntity = movieEntityManager.upsertTitleByLocale(optionalMovie.get(), title, locale);
            return movieEntity.getId();
        }
        
        MovieEntity movieEntity = movieEntityMapper.toEntity(movieDetail);
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        
        movieEntityManager.upsertTitleByLocale(savedMovie, title, locale);
        log.info("새로운 영화를 저장하였습니다.");
        
        /* 영화 정보에 해당하는 정보들을 등록합니다. */
        List<ProductionCountry> productionCountries = movieDetail.getProductionCountries();
        saveProductionCountries(savedMovie, productionCountries);
        List<MovieGenre> genres = movieDetail.getGenres();
        saveMovieGenres(movieEntity, genres);
        List<SpokenLanguage> spokenLanguages = movieDetail.getSpokenLanguages();
        saveSpokenLanguages(movieEntity, spokenLanguages);
        List<Actor> actors = movieDetail.getActors();
        saveActorEntity(movieEntity, actors);
        List<Director> directors = movieDetail.getDirectors();
        saveDirectorEntity(movieEntity, directors);
        return savedMovie.getId();
    }
    
    private void saveDirectorEntity(MovieEntity movieEntity, List<Director> directors) {
        for (Director director : directors) {
            personManager.registerAsync(director)
                    .thenAccept(personEntity -> movieEntityManager.saveDirector(movieEntity, personEntity));
        }
    }
    
    private void saveActorEntity(MovieEntity movieEntity, List<Actor> actors) {
        for (Actor actor : actors) {
            personManager.registerAsync(actor)
                    .thenAccept(personEntity -> movieEntityManager.saveActor(movieEntity, personEntity, actor));
        }
    }
    
    public void saveSpokenLanguages(MovieEntity movieEntity, List<SpokenLanguage> spokenLanguages) {
        for (SpokenLanguage spokenLanguage : spokenLanguages) {
            log.debug("spokenLanguage: {}", spokenLanguage);
            languageManager.registerAsync(spokenLanguage)
                           .thenAccept(savedLanguageEntity -> movieEntityManager.saveSpokenLanguage(movieEntity, savedLanguageEntity));
        }
    }
    
    public void saveMovieGenres(MovieEntity movieEntity, List<MovieGenre> genres) {
        for (MovieGenre genre : genres) {
            log.debug("genre: {}", genre);
            genreCodeManager.registerAsync(genre)
                            .thenAccept(savedGenreCodeEntity -> movieEntityManager.saveMovieGenre(movieEntity, savedGenreCodeEntity));
        }
    }
    
    public void saveProductionCountries(MovieEntity savedMovie, List<ProductionCountry> productionCountries) {
        for (ProductionCountry productionCountry : productionCountries) {
            log.debug("productionCountry: {}", productionCountry);
            countryManager.registerAsync(productionCountry)
                          .thenAccept(savedCountryEntity -> movieEntityManager.saveProductionCountry(savedMovie, savedCountryEntity));
        }
    }
}
