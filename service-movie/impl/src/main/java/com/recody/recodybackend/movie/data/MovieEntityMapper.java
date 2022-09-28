package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.general.MovieSource;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        imports = {MovieSource.class},
        uses = {MovieGenreMapper.class, ProductionCountryMapper.class, LanguageMapper.class})
public interface MovieEntityMapper {
    
    @Mapping(target = "tmdbId", source = "id", conditionExpression = "java(movie.getSource() == MovieSource.TMDB)")
    MovieEntity toEntity(Movie movie, @Context MovieMappingContext context);
    
//    @Mapping(target = "id", source = "iso_3166_1")
//    ProductionCountryEntity map(ProductionCountry productionCountry);

//    @Mapping(target = "id", source = "iso_639_1")
//    LanguageEntity map(SpokenLanguage language);
    
}
