package com.recody.recodybackend.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.genre.BasicGenre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class CatalogMovieDetail implements CatalogContentDetail {
    
    @JsonIgnore // 노출하지 않음.
    private String globalContentId;
    
    @JsonIgnore // 구현되지 않음.
    private String contentGroupId;
    
    private String contentId;
    
    @Builder.Default
    private BasicCategory category = BasicCategory.Movie;
    
    /* movie detail */
    private String posterPath;
    
    private String overview;
    private String title;
    private String releaseDate;
    private Integer runtime;
    private List<BasicGenre> genres;
    private String actors;
    private String directors;

}
