package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.features.CatalogMovie;
import com.recody.recodybackend.movie.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        CategoryMapper.class
})
public interface CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping(target = "imageUrl", source = "movie.posterPath")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contentId", source = "contentId")
    @Mapping(target = "title", source = "movie.title")
    CatalogContentEntity map(Movie movie);
    
    
    
    CatalogMovie mapToMovie(CatalogContentEntity entity);
}
