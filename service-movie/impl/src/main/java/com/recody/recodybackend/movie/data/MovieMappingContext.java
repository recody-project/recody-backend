package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieGenreRelation;
import com.recody.recodybackend.movie.data.movie.MovieLanguageRelation;
import com.recody.recodybackend.movie.data.movie.MovieProductionCountryRelation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMappingContext {
    
//    private final MovieGenreRepository genreRepository;
//    private final ProductionCountryRepository productionCountryRepository;
//    private final SpokenLanguageRepository spokenLanguageRepository;
    
    private MovieEntity movieEntity;
    private MovieGenreEntity genreEntity;
    private ProductionCountryEntity productionCountryEntity;
    private LanguageEntity languageEntity;
    
    @BeforeMapping
    void setEntity(@MappingTarget MovieEntity movieEntity,
                   @MappingTarget MovieGenreEntity genreEntity,
                   @MappingTarget ProductionCountryEntity productionCountryEntity,
                   @MappingTarget LanguageEntity languageEntity) {
        this.movieEntity = movieEntity;
        this.genreEntity = genreEntity;
        this.productionCountryEntity = productionCountryEntity;
        this.languageEntity = languageEntity;
    }
    
    @AfterMapping
    void setMembers(@MappingTarget MovieGenreRelation relation) {
        relation.setMovie(movieEntity);
        relation.setGenre(genreEntity);
    }
    
    @AfterMapping
    void setMembers(@MappingTarget MovieProductionCountryRelation relation) {
        relation.setMovie(movieEntity);
        relation.setProductionCountry(productionCountryEntity);
    }
    
    @AfterMapping
    void setMembers(@MappingTarget MovieLanguageRelation relation) {
        relation.setMovie(movieEntity);
        relation.setLanguage(languageEntity);
    }
}
