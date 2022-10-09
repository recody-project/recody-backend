package com.recody.recodybackend.common.contents;

import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BasicCategory implements Category{
    Movie("cat-1", "Movie", "영화", "movie"),
    TVSeries("cat-2", "TV Series", "드라마", "drama"),
    Music("cat-3", "Music", "음악", "music"),
    Book("cat-4", "Book", "책", "book"),
    Performance("cat-5", "Performance", "공연 & 전시", "performance");
    
    
    private final String englishName;
    private final String koreanName;
    private final List<String> aliases;
    
    private final String id;
    
    private static final List<String> ids = Arrays.stream(BasicCategory.values())
                                                  .map(BasicCategory::getId)
                                                  .collect(Collectors.toList());
    
    BasicCategory(String id, String englishName, String koreanName, String... aliases) {
        this.id = id;
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.aliases = Arrays.asList(aliases);
    }
    
    public static boolean isBasic(String categoryId){
        return ids.contains(categoryId);
    }
    
    public static BasicCategory idOf(String categoryId){
        if (matchesId(categoryId, Movie)){
            return BasicCategory.Movie;
        } else if (matchesId(categoryId, TVSeries)){
            return BasicCategory.TVSeries;
        } else if (matchesId(categoryId, Music)) {
            return BasicCategory.Music;
        } else if (matchesId(categoryId, Book)) {
            return BasicCategory.Book;
        } else if (matchesId(categoryId, Performance)) {
            return BasicCategory.Performance;
        } else {
            throw new UnsupportedCategoryException();
        }
    }
    
    public static BasicCategory of(String value){
        if (matches(value, Movie)) {
            return BasicCategory.Movie;
        } else if (matches(value, TVSeries)){
            return BasicCategory.TVSeries;
        } else if (matches(value, Music)) {
            return BasicCategory.Music;
        } else if (matches(value, Book)) {
            return BasicCategory.Book;
        } else if (matches(value, Performance)) {
            return BasicCategory.Performance;
        } else {
            throw new UnsupportedCategoryException();
        }
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    private static boolean matches(String value, BasicCategory category) {
        return category.aliases.contains(value) || category.englishName.equals(value) || category.koreanName.equals(value);
    }
    
    private static boolean matchesId(String id, BasicCategory category) {
        return category.id.equals(id);
    }
    
    public String getEnglishName() { return englishName; }

    public String getKoreanName() { return koreanName; }
}
