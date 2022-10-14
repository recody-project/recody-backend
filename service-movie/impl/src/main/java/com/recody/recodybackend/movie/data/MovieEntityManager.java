package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageEntity;

import java.util.Locale;

public interface MovieEntityManager {
    
    MovieEntity upsertTitleByLocale(MovieEntity movieEntity, String title, Locale locale);
    
    ProductionCountryEntity saveProductionCountry(MovieEntity savedMovie, CountryEntity savedCountry);
    
    MovieGenreEntity saveMovieGenre(MovieEntity movieEntity, MovieGenreCodeEntity genreCode);
    
    SpokenLanguageEntity saveSpokenLanguage(MovieEntity movieEntity, LanguageEntity languageEntity);
    
}
