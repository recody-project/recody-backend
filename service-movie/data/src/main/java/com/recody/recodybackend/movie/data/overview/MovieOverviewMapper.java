package com.recody.recodybackend.movie.data.overview;

import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper( componentModel = "spring",
         imports = Locale.class)
@Slf4j
public abstract class MovieOverviewMapper {
    
//    @Mapping( target = "movie", ignore = true )
//    @Mapping( target = "id", ignore = true )
//    @Mapping( target = "koreanOverview",
//              source = "overview",
//              conditionExpression = "java(locale.equals(Locale.KOREAN))" )
//    @Mapping( target = "englishOverview",
//              source = "overview",
//              conditionExpression = "java(locale.equals(Locale.ENGLISH))" )
    public MovieOverviewEntity map(String overview, @Context Locale locale){
        if (locale.getLanguage().equals( Locale.KOREAN.getLanguage() )){
            return MovieOverviewEntity.builder().koreanOverview( overview ).build();
        }
        else if (locale.getLanguage().equals( Locale.ENGLISH.getLanguage() )){
            return MovieOverviewEntity.builder().englishOverview( overview ).build();
        }
        else throw new InternalServerError("Overview 매핑에 실패하였습니다. ");
    }
    
    public String map(MovieOverviewEntity entity, @Context Locale locale) {
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            if ( entity.getKoreanOverview() != null ) {
                return entity.getKoreanOverview();
            }
            else {
                return entity.getEnglishOverview();
            }
        }
        else if ( locale.getLanguage().equals( Locale.ENGLISH.getLanguage() ) ) {
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
    
    public MovieOverviewEntity update(MovieEntity entity,
                                              TMDBMovieDetail detail,
                                              Locale locale) {
        MovieOverviewEntity overviewEntity = entity.getOverview();
        String newOverview = detail.getOverview();
        
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            String koreanOverview = overviewEntity.getKoreanOverview();
            if ( koreanOverview == null ) {
                overviewEntity.setKoreanOverview( newOverview );
                return overviewEntity;
            }
            else {
                return overviewEntity;
            }
        }
        else if ( locale.getLanguage().equals( Locale.ENGLISH.getLanguage() ) ) {
            String englishOverview = overviewEntity.getEnglishOverview();
            if ( englishOverview == null ) {
                overviewEntity.setEnglishOverview( newOverview );
                return overviewEntity;
            }
            else {
                return overviewEntity;
            }
        }
        else return overviewEntity;
    }
    
    @AfterMapping
    public void setOverviewBidirectionally(@MappingTarget MovieEntity movieEntity){
        log.debug("mapping movieEntity: ");
        // title entity 에 movie 를 세팅해준다.
        MovieOverviewEntity title = movieEntity.getOverview();
        movieEntity.setOverview(title);
        log.debug("title for this overview: {}", movieEntity.getOriginalTitle());
    }
}
