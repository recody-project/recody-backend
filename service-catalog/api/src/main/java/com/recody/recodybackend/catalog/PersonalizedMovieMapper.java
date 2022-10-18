package com.recody.recodybackend.catalog;


import com.recody.recodybackend.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PersonalizedMovieMapper {
    
    @Mapping(target = "contentId", source = "movie.contentId")
    @Mapping(target = "personalizedUserId", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "category", ignore = true)
    public abstract PersonalizedMovieDetail map(Movie movie);
    
}
