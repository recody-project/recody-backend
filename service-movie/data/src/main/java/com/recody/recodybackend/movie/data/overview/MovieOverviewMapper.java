package com.recody.recodybackend.movie.data.overview;

import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.movie.data.event.NoEnglishOverviewFoundEventPublisher;
import com.recody.recodybackend.movie.data.event.NoKoreanOverviewFoundEventPublisher;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.events.NoEnglishOverviewFound;
import com.recody.recodybackend.movie.events.NoKoreanOverviewFound;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper( componentModel = "spring",
         imports = Locale.class )
@Slf4j
public abstract class MovieOverviewMapper {
    
    @Autowired
    private NoEnglishOverviewFoundEventPublisher noEnglishOverviewFoundEventPublisher;
    
    @Autowired
    private NoKoreanOverviewFoundEventPublisher noKoreanOverviewFoundEventPublisher;
    
    public MovieOverviewEntity map(String overview, @Context Locale locale) {
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            return MovieOverviewEntity.builder().koreanOverview( overview ).build();
        }
        else if ( locale.getLanguage().equals( Locale.ENGLISH.getLanguage() ) ) {
            return MovieOverviewEntity.builder().englishOverview( overview ).build();
        }
        else throw new InternalServerError( "Overview 매핑에 실패하였습니다. " );
    }
    
    public String map(MovieOverviewEntity entity, @Context Locale locale) {
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            if ( StringUtils.hasText( entity.getKoreanOverview() ) ) {
                return entity.getKoreanOverview();
            }
            else {
                log.warn( "한국어 Overview 를 쿼리했으나 없었습니다. overviewId: {}", entity.getId());
                noKoreanOverviewFoundEventPublisher.publish( new NoKoreanOverviewFound( entity.getId() ) );
                return entity.getEnglishOverview();
            }
        }
        else if ( locale.getLanguage().equals( Locale.ENGLISH.getLanguage() ) ) {
            if ( StringUtils.hasText( entity.getEnglishOverview() ) ) {
                return entity.getEnglishOverview();
            }
            else {
                log.warn( "영어 Overview 를 쿼리했으나 없었습니다. overviewId: {}", entity.getId());
                noEnglishOverviewFoundEventPublisher.publish( new NoEnglishOverviewFound( entity.getId() ) );
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
    public void setOverviewBidirectionally(@MappingTarget MovieEntity movieEntity) {
        log.debug( "mapping movieEntity: " );
        // title entity 에 movie 를 세팅해준다.
        MovieOverviewEntity title = movieEntity.getOverview();
        movieEntity.setOverview( title );
        log.debug( "title for this overview: {}", movieEntity.getOriginalTitle() );
    }
}
