package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.people.MovieActorEntity;
import com.recody.recodybackend.movie.data.people.MovieDirectorEntity;
import com.recody.recodybackend.movie.data.people.MoviePersonEntity;
import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageEntity;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Transactional
public interface MovieEntityManager {
    
    @Async
    @Transactional
    default CompletableFuture<List<MovieGenreEntity>> saveAsync(MovieEntity movieEntity, List<MovieGenreCodeEntity> genreCodeEntities){
        return CompletableFuture.completedFuture(this.saveMovieGenre(movieEntity, genreCodeEntities));
    }
    

    default List<MovieGenreEntity> saveMovieGenre(MovieEntity movieEntity, List<MovieGenreCodeEntity> genreCodes) {
        return genreCodes.stream()
                         .map(genreCodeEntity -> this.saveMovieGenre(movieEntity, genreCodeEntity))
                         .collect(Collectors.toList());
    }

    @Transactional
    default List<ProductionCountryEntity> saveProductionCountry(MovieEntity savedMovie,
                                                                List<CountryEntity> savedCountries) {
        return savedCountries.stream()
                             .map(countryEntity -> this.saveProductionCountry(savedMovie, countryEntity))
                             .collect(Collectors.toList());
    }
    
    @Async
    @Transactional
    default CompletableFuture<List<ProductionCountryEntity>> saveProductionCountryAsync(MovieEntity savedMovie,
                                                                List<CountryEntity> savedCountries) {
        return CompletableFuture.completedFuture(this.saveProductionCountry(savedMovie, savedCountries));
    }
    
    @Transactional
    default List<SpokenLanguageEntity> saveSpokenLanguage(MovieEntity movieEntity,
                                                          List<LanguageEntity> languageEntities) {
        return languageEntities.stream()
                               .map(languageEntity -> this.saveSpokenLanguage(movieEntity, languageEntity))
                               .collect(Collectors.toList());
    }
    
    @Async
    @Transactional
    default CompletableFuture<List<SpokenLanguageEntity>> saveSpokenLanguageAsync(MovieEntity movieEntity,
                                                          List<LanguageEntity> languageEntities) {
        return CompletableFuture.completedFuture(this.saveSpokenLanguage(movieEntity, languageEntities));
        
    }
    
    
    
    
    MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale);
    ProductionCountryEntity saveProductionCountry(MovieEntity savedMovie, CountryEntity savedCountry);
    MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode);
    SpokenLanguageEntity saveSpokenLanguage(MovieEntity movieEntity, LanguageEntity languageEntity);
    MovieActorEntity saveActor(MovieEntity movieEntity, MoviePersonEntity personEntity, TMDBCast cast);
    MovieDirectorEntity saveDirector(MovieEntity movieEntity, MoviePersonEntity personEntity);
    List<MovieDirectorEntity> saveDirector(MovieEntity movieEntity,List<MoviePersonEntity> personEntities);
    
}
