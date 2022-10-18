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
import com.recody.recodybackend.movie.features.getmoviecredit.Actor;

import java.util.Locale;

public interface MovieEntityManager {
    
    MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale);
    
    ProductionCountryEntity saveProductionCountry(MovieEntity savedMovie, CountryEntity savedCountry);
    
    MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode);
    
    SpokenLanguageEntity saveSpokenLanguage(MovieEntity movieEntity, LanguageEntity languageEntity);
    
    MovieActorEntity saveActor(MovieEntity movieEntity, MoviePersonEntity personEntity, Actor actor);
    MovieDirectorEntity saveDirector(MovieEntity movieEntity, MoviePersonEntity personEntity);
}
