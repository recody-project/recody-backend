package com.recody.recodybackend.common.contents;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public enum Category {
    Movie("Movie", "영화", "movie"),
    TVSeries("TV Series", "드라마", "drama"),
    Music("Music", "음악", "music"),
    Book("Book", "책", "book"),
    Performance("Performance", "공연 & 전시", "performance");
    
    
    private final String englishName;
    private final String koreanName;
    private final List<String> aliases;
    
    Category(String englishName, String koreanName, String... aliases) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.aliases = Arrays.asList(aliases);
    }
    
    public static Category of(String value){
        if (matches(value, Movie)) {
            return Category.Movie;
        } else if (matches(value, TVSeries)){
            return Category.TVSeries;
        } else if (matches(value, Music)) {
            return Category.Music;
        } else if (matches(value, Book)) {
            return Category.Book;
        } else if (matches(value, Performance)) {
            return Category.Performance;
        } else {
            throw new ApplicationException(GlobalErrorType.UnsupportedCategory, HttpStatus.BAD_REQUEST, "지원하지 않는 카테고리입니다.");
        }
    }
    
    private static boolean matches(String value, Category category) {
        return category.aliases.contains(value) || category.englishName.equals(value) || category.koreanName.equals(value);
    }
    
    public String getEnglishName() { return englishName; }

    public String getKoreanName() { return koreanName; }
}
