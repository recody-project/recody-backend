package com.recody.recodybackend.movie.data.people;

import org.mapstruct.Mapper;

import java.util.Locale;

@Mapper(componentModel = "spring")
abstract class MoviePersonNameMapper {
    
    public MoviePersonNameEntity map(String name, Locale locale) {
        if (locale.equals(Locale.KOREAN)){
            return MoviePersonNameEntity.builder()
                                        .koreanName(name)
                                        .build();
        }
        return MoviePersonNameEntity.builder()
                                    .englishName(name)
                                    .build();
    }
    
    public MoviePersonNameEntity map(String name) {
        // 현재 영어이름만 받을 수 있어서 조건 없이 영어이름으로 매핑.
        return MoviePersonNameEntity.builder()
                                    .englishName(name)
                                    .build();
    }
    
//    public abstract MoviePersonNameEntity update(@MappingTarget MoviePersonNameEntity entity, String name, @Context Locale locale);
}
