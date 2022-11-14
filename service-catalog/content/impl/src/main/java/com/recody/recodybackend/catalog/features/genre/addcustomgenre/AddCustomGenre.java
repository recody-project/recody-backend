package com.recody.recodybackend.catalog.features.genre.addcustomgenre;

import com.recody.recodybackend.category.CategoryId;
import com.recody.recodybackend.genre.CustomGenreIconUrl;
import com.recody.recodybackend.genre.CustomGenreName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCustomGenre {
    
    private Long userId;
    private CustomGenreName genreName;
    
    private CustomGenreIconUrl genreIconUrl;
    private CategoryId categoryId;
    
    @Override
    public String toString() {
        return "{\"AddCustomGenre\":{"
               + "\"userId\":" + userId
               + ", \"genreName\":" + genreName
               + ", \"categoryId\":" + categoryId
               + "}}";
    }
}
