package com.recody.recodybackend.catalog;


import com.recody.recodybackend.movie.MovieDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PersonalizedMovieMapper {
    
    @Mapping(target = "contentId", source = "movieDetail.contentId")
    @Mapping(target = "personalizedUserId", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "revenue", ignore = true)
    @Mapping(target = "popularity", ignore = true)
    @Mapping(target = "originalLanguage", ignore = true)
    @Mapping(target = "originalTitle", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "voteAverage", ignore = true)
    @Mapping(target = "voteCount", ignore = true)
    @Mapping(target = "spokenLanguages", ignore = true)
    public abstract PersonalizedMovieDetail map(MovieDetail movieDetail);
    
}
