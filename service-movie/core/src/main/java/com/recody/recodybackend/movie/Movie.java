package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.BasicContent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Setter
public class Movie implements BasicContent {
    
    @NonNull
    private String contentId;

    @Builder.Default
    private BasicCategory category = BasicCategory.Movie;

    // TODO: imageUrl 로 바꾸기
    private String posterPath;

    private String title;
    
    @Override
    public String toString() {
        return "{\"Movie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getContentId(), movie.getContentId()) && Objects.equals(getCategory(),
                                                                                      movie.getCategory());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getContentId(), getCategory());
    }
}
