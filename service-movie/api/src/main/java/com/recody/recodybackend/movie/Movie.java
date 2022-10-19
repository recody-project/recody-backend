package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.BasicContent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Setter
public class Movie implements BasicContent {
    
    private String contentId;

    @Builder.Default
    private BasicCategory category = BasicCategory.Movie;

    private String posterPath;

    private String title;
    
    @Override
    public String toString() {
        return "{\"Movie\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}";
    }
}
