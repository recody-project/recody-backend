package com.recody.recodybackend.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PersonalizedMovieDetail implements PersonalizedContentDetail {
    
    private Long personalizedUserId;
    private String contentId;
    private Category category;
    
    @JsonIgnore // 노출하지 않음.
    private String globalContentId;
    
    @JsonIgnore // 구현되지 않음.
    private String contentGroupId;
    
    /* movie detail */
    private String posterPath;
    private String overview;
    private String title;
    private String releaseDate;
    private Integer runtime;
    
    private List<? extends Genre> genres;
    private List<Actor> actors;
    private List<Director> directors;
}
