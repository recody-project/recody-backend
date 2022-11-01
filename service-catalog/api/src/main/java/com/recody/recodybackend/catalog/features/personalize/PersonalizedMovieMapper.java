package com.recody.recodybackend.catalog.features.personalize;


import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.catalog.PersonalizedMovie;
import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring" )
public abstract class PersonalizedMovieMapper {
    

    @Mapping( target = "globalContentId", ignore = true )
    @Mapping( target = "contentGroupId", ignore = true )
    @Mapping( target = "contentId", source = "movieDetail.contentId" )
    @Mapping( target = "personalizedUserId", ignore = true )
    @Mapping( target = "genres", ignore = true )
    @Mapping( target = "category", ignore = true )
//    @Mapping( target = "revenue", ignore = true )
//    @Mapping( target = "popularity", ignore = true )
//    @Mapping( target = "originalLanguage", ignore = true )
//    @Mapping( target = "originalTitle", ignore = true )
//    @Mapping( target = "status", ignore = true )
//    @Mapping( target = "voteAverage", ignore = true )
//    @Mapping( target = "voteCount", ignore = true )
//    @Mapping( target = "spokenLanguages", ignore = true )
    public abstract PersonalizedMovieDetail map(MovieDetail movieDetail);
    
    @Mapping( target = "personalizedUserId", ignore = true )
    public abstract PersonalizedMovieDetail map(CatalogMovieDetail movieDetail);
    

    @Mapping( target = "category", source = "category", defaultExpression = "java(movieDetail.getCategory())")
    @Mapping( target = "genres", source = "genres", defaultExpression = "java(movieDetail.getGenres())")
    @Mapping( target = "personalizedUserId", source = "userId")
    public abstract PersonalizedMovieDetail map(CatalogMovieDetail movieDetail,
                                                Category category,
                                                List<Genre> genres,
                                                Long userId);
    
    
    
    @Mapping( target = "contentId", source = "movie.contentId" )
    @Mapping( target = "personalizedUserId", ignore = true )
    @Mapping( target = "category", ignore = true )
    public abstract PersonalizedMovie map(Movie movie);
    
}
