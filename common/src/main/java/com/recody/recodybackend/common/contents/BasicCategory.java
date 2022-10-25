package com.recody.recodybackend.common.contents;

import com.fasterxml.jackson.annotation.*;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class BasicCategory implements Category {
    
    private static final List<String> Ids = List.of("cat-1", "cat-2", "cat-3", "cat-4", "cat-5");
    
    public static final BasicCategory Movie = new BasicCategory(Ids.get(0), "Movie", "영화", "movie");
    public static final BasicCategory TVSeries = new BasicCategory(Ids.get(1), "TV Series", "드라마", "drama");
    public static final BasicCategory Music = new BasicCategory(Ids.get(2), "Music", "음악", "music");
    public static final BasicCategory Book = new BasicCategory(Ids.get(3), "Book", "책", "book");
    public static final BasicCategory Performance = new BasicCategory(Ids.get(4), "Performance", "공연 & 전시", "performance");
    
    
    private String id;
    private String name;
    @JsonIgnore
    private String koreanName;
    private List<String> aliases;
    
    BasicCategory(String id, String englishName, String koreanName, String... aliases) {
        this.id = id;
        this.name = englishName;
        this.koreanName = koreanName;
        this.aliases = Arrays.asList(aliases);
    }
    
    public static boolean isBasic(String categoryId) {
        return Ids.contains(categoryId);
    }
    
    public static BasicCategory idOf(String categoryId) {
        if (matchesId(categoryId, Movie)) {
            return BasicCategory.Movie;
        } else if (matchesId(categoryId, TVSeries)) {
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
    
    public static BasicCategory of(String value) {
        if (matches(value, Movie)) {
            return BasicCategory.Movie;
        } else if (matches(value, TVSeries)) {
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
    
    @JsonIgnore
    public String getEnglishName() {
        return name;
    }
    
    public String getKoreanName() {
        return koreanName;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isBasic() {
        return true;
    }
    
    private static boolean matches(String value, BasicCategory category) {
        return category.aliases.contains(value) || category.name.equals(value) || category.koreanName.equals(
                value);
    }
    
    private static boolean matchesId(String id, BasicCategory category) {
        return category.id.equals(id);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicCategory)) return false;
        BasicCategory that = (BasicCategory) o;
        return Objects.equals(getId(), that.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    
    @Override
    public String toString() {
        return "{\"BasicCategory\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
