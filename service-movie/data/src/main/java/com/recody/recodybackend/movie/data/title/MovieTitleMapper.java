package com.recody.recodybackend.movie.data.title;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class MovieTitleMapper {
    
    public MovieTitleEntity map(String title, @Context Locale locale) {
        log.debug("mapping title: {}", title);
        MovieTitleEntity movieTitleEntity;
        if (locale.equals(Locale.ENGLISH)) {
            movieTitleEntity = MovieTitleEntity.builder().englishTitle(title).build();
        } else if (locale.equals(Locale.KOREAN)) {
            movieTitleEntity = MovieTitleEntity.builder().koreanTitle(title).build();
        } else throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "영화 제목을 저장하지 못했습니다.");
        log.debug("mapped title: {}", movieTitleEntity);
        return movieTitleEntity;
    }
    
    // TODO original title TitleEntity 로 이동하기
    public String map(MovieEntity entity, @Context Locale locale) {
        if (locale.equals(Locale.KOREAN)) {
            String koreanTitle = entity.getTitle().getKoreanTitle();
            if (koreanTitle == null) {
                return entity.getOriginalTitle();
            }
            return koreanTitle;
        }
        if (locale.equals(Locale.ENGLISH)) {
            String englishTitle = entity.getTitle().getEnglishTitle();
            if (englishTitle == null) {
                return entity.getOriginalTitle();
            }
            return englishTitle;
        }
        return entity.getOriginalTitle();
    }
    
    public String map(MovieTitleEntity entity, @Context Locale locale) {
        if (locale.equals(Locale.KOREAN)) {
            String koreanTitle = entity.getKoreanTitle();
            if (koreanTitle != null) {
                return koreanTitle;
            }
        }
        if (locale.equals(Locale.ENGLISH)) {
            String englishTitle = entity.getEnglishTitle();
            if (englishTitle != null) {
                return englishTitle;
            }
        }
        return entity.getEnglishTitle();
    }
    
    @AfterMapping
    public void invokeBidirectionalTitleSetter(@MappingTarget MovieEntity movieEntity){
        log.debug("mapping movieEntity: ");
        // title entity 에 movie 를 세팅해준다.
        MovieTitleEntity title = movieEntity.getTitle();
        movieEntity.setTitle(title);
        log.debug("title: {}", movieEntity.getTitle());
    }
}
