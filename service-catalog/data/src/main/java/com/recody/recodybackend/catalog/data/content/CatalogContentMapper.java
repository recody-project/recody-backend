package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.content.CatalogMovie;
import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.record.RecordContent;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper( componentModel = "spring",
         builder = @Builder(disableBuilder = true),
         imports = {BasicCategory.class, CategoryEntity.class},
         uses = {CategoryMapper.class, CatalogContentTitleMapper.class} )
@Slf4j
public abstract class CatalogContentMapper {
    
    
    // TODO: 국가별 이름 매핑하기
    @Mapping( target = "records", ignore = true )
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
    
    @Mapping( target = "title", ignore = true )
    @Mapping( target = "records", ignore = true )
    @Mapping( target = "category", expression = "java(CategoryEntity.builder().id( event.getCategoryId() ).name( event.getCategoryName() ).build())")
    @Mapping(target = "id", source = "event.catalogId")
    public abstract CatalogContentEntity map(ContentCreated event);
    
    @Mapping( target = "title", source = "contentEntity", qualifiedByName = "titleMapper")
    @Mapping( target = "recordId", source = "recordEntity.recordId" )
    @Mapping( target = "lastModifiedAt", source = "recordEntity.lastModifiedAt")
    public abstract RecordContent map(CatalogContentEntity contentEntity, RecordEntity recordEntity, @Context Locale locale);
    
    @Named( "titleMapper" )
    String mapTitle(CatalogContentEntity entity, @Context Locale locale){
        String koreanTitle = entity.getTitle().getKoreanTitle();
        String englishTitle = entity.getTitle().getEnglishTitle();
    
        if ( locale.equals( Locale.KOREAN ) ) {
            if (koreanTitle == null){
                log.debug( "한국어 타이틀이 없어서 영어 타이들을 매핑: {}", englishTitle );
                return englishTitle;
            }
            return koreanTitle;
        }
        else {
            if (englishTitle == null){
                log.debug( "영어 타이틀이 없어서 한국어 타이들을 매핑: {}", koreanTitle );
                return koreanTitle;
            }
            return englishTitle;
        }
    }
    @AfterMapping
    void setBidirectional(@MappingTarget CatalogContentEntity content, MovieDetail detail, @Context Locale locale){
        CatalogContentTitleEntity titleEntity = content.getTitle();
        titleEntity.setContent( content );
        log.debug( "title 에 content 를 세팅하였음." );
    }
}
