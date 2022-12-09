package com.recody.recodybackend.movie.data.overview;

import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper( componentModel = "spring",
         imports = Locale.class)
@Slf4j
public abstract class MovieOverviewMapper {
    
    @Mapping( target = "movie", ignore = true )
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "koreanOverview",
              source = "overview",
              conditionExpression = "java(locale.equals(Locale.KOREAN))" )
    @Mapping( target = "englishOverview",
              source = "overview",
              conditionExpression = "java(locale.equals(Locale.ENGLISH))" )
    public abstract MovieOverviewEntity map(String overview, @Context Locale locale);
    
    public String map(MovieOverviewEntity entity, @Context Locale locale) {
        if ( locale.equals( Locale.KOREAN ) ) {
            if ( entity.getKoreanOverview() != null ) {
                return entity.getKoreanOverview();
            }
            else {
                return entity.getEnglishOverview();
            }
        }
        else if ( locale.equals( Locale.ENGLISH ) ) {
            if ( entity.getEnglishOverview() != null ) {
                return entity.getEnglishOverview();
            }
            else {
                return entity.getKoreanOverview();
            }
        }
        else {
            throw new InternalServerError( "Overview 를 매핑하지 못했습니다." );
        }
    }
    
    @AfterMapping
    public void setOverviewBidirectionally(@MappingTarget MovieEntity movieEntity){
        log.debug("mapping movieEntity: ");
        // title entity 에 movie 를 세팅해준다.
        MovieOverviewEntity title = movieEntity.getOverview();
        movieEntity.setOverview(title);
        log.debug("overview: {}", movieEntity.getOriginalTitle());
    }
}
