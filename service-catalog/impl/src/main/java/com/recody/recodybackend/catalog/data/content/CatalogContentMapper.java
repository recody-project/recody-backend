package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.CatalogMovie;
import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.MovieDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring", imports = {BasicCategory.class}, uses = {CategoryMapper.class} )
public interface CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping( target = "imageUrl", source = "movieDetail.posterPath" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "contentId", source = "contentId" )
    @Mapping( target = "title", source = "movieDetail.title" )
    CatalogContentEntity newEntity(MovieDetail movieDetail);
    
    @Mapping( target = "posterPath", source = "entity.imageUrl" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true )
    @Mapping( target = "category", expression = "java(BasicCategory.idOf(entity.getCategory().getId()))" )
    CatalogMovie toCatalogMovie(CatalogContentEntity entity);
    
    @Mapping( target = "title", source = "movieDetail.title" )
    @Mapping( target = "category", source = "movieDetail.category" )
    @Mapping( target = "contentId", source = "movieDetail.contentId" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true ) // 구현되지 않음.
    CatalogMovieDetail toCatalogMovieDetail(CatalogContentEntity entity, MovieDetail movieDetail);
}
