package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.data.event.NoKoreanPersonNameFoundEventPublisher;
import com.recody.recodybackend.movie.events.NoKoreanPersonNameFound;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper( componentModel = "spring" )
@Slf4j
abstract class MoviePersonNameMapper {
    
    @Autowired
    private NoKoreanPersonNameFoundEventPublisher publisher;
    
    public MoviePersonNameEntity map(String name, Locale locale) {
        if ( locale.equals( Locale.KOREAN ) ) {
            return MoviePersonNameEntity.builder()
                                        .koreanName( name )
                                        .build();
        }
        return MoviePersonNameEntity.builder()
                                    .englishName( name )
                                    .build();
    }
    
    public MoviePersonNameEntity map(String name) {
        // 현재 영어이름만 받을 수 있어서 조건 없이 영어이름으로 매핑.
        return MoviePersonNameEntity.builder()
                                    .englishName( name )
                                    .build();
    }
    
    // name entity 를 String 으로 매핑한다. locale 에 따라 영어 또는 한국어로 매핑한다.
    public String map(MoviePersonNameEntity entity, @Context Locale locale) {
        log.debug( "mapping entity to String: {}, locale: {}", entity, locale );
        String koreanName = entity.getKoreanName();
        String englishName = entity.getEnglishName();
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            if ( StringUtils.hasText( koreanName ) ) {
                return koreanName;
            }
            else {
                log.debug( "한글이 없어서 영어 이름을 반환 englishName: {}", englishName );
                publisher.publish( new NoKoreanPersonNameFound( entity.getId() ) );
                return englishName;
            }
        }
        else {
            if ( StringUtils.hasText( englishName ) ) {
                return englishName;
            }
            else {
                return koreanName;
            }
        }
    }

//    public abstract MoviePersonNameEntity update(@MappingTarget MoviePersonNameEntity entity, String name, @Context Locale locale);
}
