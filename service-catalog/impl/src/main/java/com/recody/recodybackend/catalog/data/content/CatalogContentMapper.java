package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.CatalogMovie;
import com.recody.recodybackend.catalog.CatalogMovieDetail;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.movie.MovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper( componentModel = "spring",
         builder = @Builder(disableBuilder = true),
         imports = {BasicCategory.class},
         uses = {CategoryMapper.class, CatalogContentTitleMapper.class} )
@Slf4j
public abstract class CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping( target = "imageUrl", source = "movieDetail.posterPath" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "contentId", source = "contentId" )
    @Mapping( target = "title", source = "movieDetail.title")
    public abstract CatalogContentEntity newEntity(MovieDetail movieDetail, @Context Locale locale);
    
    @Mapping( target = "posterPath", source = "entity.imageUrl" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true )
    @Mapping( target = "category", expression = "java(BasicCategory.idOf(entity.getCategory().getId()))" )
    public abstract CatalogMovie toCatalogMovie(CatalogContentEntity entity, @Context Locale locale);
    
    @Mapping( target = "title", source = "movieDetail.title" )
    @Mapping( target = "category", source = "movieDetail.category" )
    @Mapping( target = "contentId", source = "movieDetail.contentId" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true ) // 구현되지 않음.
    public abstract CatalogMovieDetail toCatalogMovieDetail(CatalogContentEntity entity, MovieDetail movieDetail);
    
    @AfterMapping
    void setBidirectional(@MappingTarget CatalogContentEntity content, MovieDetail detail, @Context Locale locale){
        CatalogContentTitleEntity titleEntity = content.getTitle();
        titleEntity.setContent( content );
        log.debug( "title 에 content 를 세팅하였음." );
    }
}
