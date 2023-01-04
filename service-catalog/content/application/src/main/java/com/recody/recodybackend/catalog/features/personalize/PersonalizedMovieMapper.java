package com.recody.recodybackend.catalog.features.personalize;


import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.content.PersonalizedMovie;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieDetailViewModel;
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
    public abstract PersonalizedMovieDetail map(MovieDetailViewModel movieDetail);
    
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
