package com.recody.recodybackend.genre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
public class CustomGenre implements Genre {
    
    @JsonUnwrapped
    private CustomGenreId genreId;
    
    @JsonUnwrapped
    private CustomGenreName genreName;
    
    @JsonUnwrapped
    private CustomGenreIconUrl genreIconUrl;
    @JsonIgnore
    private Category category;
    
    private CustomGenre(String genreId, String genreName, String genreIconUrl, Category category) {
        this.genreId = CustomGenreId.of( genreId );
        this.genreName = CustomGenreName.of( genreName );
        this.genreIconUrl = CustomGenreIconUrl.of( genreIconUrl );
        this.category = category;
    }
    
    private CustomGenre(CustomGenreId genreId, CustomGenreName genreName, CustomGenreIconUrl genreIconUrl, Category category) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreIconUrl = genreIconUrl;
        this.category = category;
    }
    
    public static CustomGenre of(CustomGenreId genreId, CustomGenreName genreName, CustomGenreIconUrl genreIconUrl, Category category) {
        return new CustomGenre( genreId, genreName, genreIconUrl, category );
    }
    
    public static CustomGenre of(String genreId, String genreName, String genreIconUrl, Category category) {
        return new CustomGenre( genreId, genreName, genreIconUrl, category );
    }
    
    public String getGenreId() {
        return genreId.getValue();
    }
    
    public Category getCategory() {
        return category;
    }
    
    @Override
    public String toString() {
        return "{\"CustomGenre\":{"
               + "\"genreId\":" + ((genreId != null) ? ("\"" + genreId + "\"") : null)
               + ", \"category\":" + category
               + "}}";
    }
}
