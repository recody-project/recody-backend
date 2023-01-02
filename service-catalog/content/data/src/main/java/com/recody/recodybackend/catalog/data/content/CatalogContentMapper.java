package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.event.ContentCreated;
import com.recody.recodybackend.content.CatalogMovie;
import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper( componentModel = "spring",
         builder = @Builder( disableBuilder = true ),
         imports = {BasicCategory.class, CategoryEntity.class},
         uses = {CategoryMapper.class, CatalogContentTitleMapper.class} )
@Slf4j
public abstract class CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping( target = "imageUrl", source = "movieDetail.imageUrl" )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "contentId", source = "movieDetail.contentId" )
    @Mapping( target = "title", source = "movieDetail.title" )
    public abstract CatalogContentEntity newEntity(MovieDetailViewModel movieDetail, @Context Locale locale);
    
    @Mapping( target = "posterPath", source = "entity.imageUrl" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true )
    @Mapping( target = "category", expression = "java(BasicCategory.idOf(entity.getCategory().getId()))" )
    public abstract CatalogMovie toCatalogMovie(CatalogContentEntity entity, @Context Locale locale);
    
    @Mapping( target = "posterPath", source = "movieDetail.imageUrl" )
    @Mapping( target = "title", source = "movieDetail.title" )
    @Mapping( target = "category", source = "movieDetail.category" )
    @Mapping( target = "contentId", source = "movieDetail.contentId" )
    @Mapping( target = "globalContentId", source = "entity.id" )
    @Mapping( target = "contentGroupId", ignore = true ) // 구현되지 않음.
    public abstract CatalogMovieDetail toCatalogMovieDetail(CatalogContentEntity entity, MovieDetailViewModel movieDetail);
    
    
    @Mapping( target = "posterPath", source = "viewModel.imageUrl" )
    @Mapping( target = "contentGroupId", ignore = true )
    @Mapping( target = "globalContentId", ignore = true )
    public abstract CatalogMovieDetail toCatalogMovieDetail(MovieDetailViewModel viewModel,
                                                            @Context Locale locale);
    @Mapping( target = "title", ignore = true )
    @Mapping( target = "category",
              expression = "java(CategoryEntity.builder().id( event.getCategoryId() ).name( event.getCategoryName() ).build())" )
    @Mapping( target = "id", source = "event.catalogId" )
    public abstract CatalogContentEntity map(ContentCreated event);
    
    @Named( "titleMapper" )
    String mapTitle(CatalogContentEntity entity, @Context Locale locale) {
        String koreanTitle = entity.getTitle().getKoreanTitle();
        String englishTitle = entity.getTitle().getEnglishTitle();
        
        if ( locale.equals( Locale.KOREAN ) ) {
            if ( koreanTitle == null ) {
                log.debug( "한국어 타이틀이 없어서 영어 타이들을 매핑: {}", englishTitle );
                return englishTitle;
            }
            return koreanTitle;
        }
        else {
            if ( englishTitle == null ) {
                log.debug( "영어 타이틀이 없어서 한국어 타이들을 매핑: {}", koreanTitle );
                return koreanTitle;
            }
            return englishTitle;
        }
    }
    
    @AfterMapping
    void setBidirectional(@MappingTarget CatalogContentEntity content, MovieDetailViewModel detail, @Context Locale locale) {
        CatalogContentTitleEntity titleEntity = content.getTitle();
        titleEntity.setContent( content );
        log.debug( "title 에 content 를 세팅하였음." );
    }
}
