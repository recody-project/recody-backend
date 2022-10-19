package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.features.CatalogMovie;
import com.recody.recodybackend.movie.MovieDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        CategoryMapper.class
})
public interface CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping(target = "imageUrl", source = "movieDetail.posterPath")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contentId", source = "contentId")
    @Mapping(target = "title", source = "movieDetail.title")
    CatalogContentEntity map(MovieDetail movieDetail);
    
    
    
    CatalogMovie mapToMovie(CatalogContentEntity entity);
}
