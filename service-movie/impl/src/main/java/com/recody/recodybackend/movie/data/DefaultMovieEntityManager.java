package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreRepository;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.people.*;
import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryRepository;
import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageRepository;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import com.recody.recodybackend.movie.data.title.MovieTitleRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
class DefaultMovieEntityManager implements MovieEntityManager {
    
    private final MovieTitleRepository movieTitleRepository;
    
    private final ProductionCountryRepository productionCountryRepository;
    
    private final SpokenLanguageRepository spokenLanguageRepository;
    
    private final MovieGenreRepository movieGenreRepository;
    
    private final MovieActorRepository actorRepository;
    private final MovieDirectorRepository directorRepository;
    
    @Override
    @Transactional
    public MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale) {
        // 타이틀이 없으면 새로 만들어 저장하고
        // 타이틀이 있으면 업데이트한다.
        Optional<MovieTitleEntity> optionalTitle = movieTitleRepository.findByMovie(movieEntity);
        if (optionalTitle.isEmpty()) {
            MovieTitleEntity movieTitleEntity = new MovieTitleEntity();
            movieTitleEntity.setMovie(movieEntity);
            updateTitleByLocale(movieTitleEntity, title, locale);
            movieTitleRepository.save(movieTitleEntity);
            return movieEntity;
        }
        MovieTitleEntity movieTitleEntity = optionalTitle.get();
        updateTitleByLocale(movieTitleEntity, title, locale);
        return movieEntity;
    }
    
    @Override
    @Transactional
    public ProductionCountryEntity saveProductionCountry(MovieEntity savedMovie, CountryEntity savedCountry) {
        log.debug("saving production country");
        ProductionCountryEntity productionCountryEntity = ProductionCountryEntity.builder()
                                                                                 .movie(savedMovie)
                                                                                 .country(savedCountry)
                                                                                 .build();
        return productionCountryRepository.save(productionCountryEntity);
    }
    
    @Override
    @Transactional
    public MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode) {
        log.debug("saving movie gnere");
    
        MovieGenreEntity movieGenreEntity = MovieGenreEntity.builder().genre(genreCode).movie(movieEntity).build();
        return movieGenreRepository.save(movieGenreEntity);
    }
    
    @Override
    @Transactional
    public SpokenLanguageEntity saveSpokenLanguage(MovieEntity movieEntity, LanguageEntity languageEntity) {
        log.debug("saving spoken language");
        SpokenLanguageEntity spokenLanguageEntity = SpokenLanguageEntity.builder()
                                                                        .language(languageEntity)
                                                                        .movie(movieEntity)
                                                                        .build();
        return spokenLanguageRepository.save(spokenLanguageEntity);
    }
    
    @Override
    @Transactional
    public MovieActorEntity saveActor(MovieEntity movieEntity, MoviePersonEntity personEntity, Actor actor) {
        log.debug("saving Actor Entity");
        MovieActorEntity actorEntity = MovieActorEntity.builder().character(actor.getCharacter()).movie(movieEntity).person(personEntity).build();
        return actorRepository.save(actorEntity);
    }
    
    @Override
    @Transactional
    public MovieDirectorEntity saveDirector(MovieEntity movieEntity, MoviePersonEntity personEntity) {
        log.debug("saving Actor Entity");
        MovieDirectorEntity actorEntity = MovieDirectorEntity.builder().movie(movieEntity).person(personEntity).build();
        return directorRepository.save(actorEntity);
    }
    
    
    private void updateTitleByLocale(MovieTitleEntity movieTitleEntity, String title, Locale locale) {
        if (locale.equals(Locale.KOREAN)){
            movieTitleEntity.setKoreanTitle(title);
        }
        if (locale.equals(Locale.ENGLISH)){
            movieTitleEntity.setEnglishTitle(title);
        }
    }
}
