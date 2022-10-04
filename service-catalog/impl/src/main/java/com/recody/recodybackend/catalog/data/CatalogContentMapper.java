package com.recody.recodybackend.catalog.data;

import com.recody.recodybackend.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CatalogContentMapper {
    
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contentId", source = "movieId")
    CatalogContentEntity map(Movie movie);
    
    Movie mapToMovie(CatalogContentEntity entity);
}
