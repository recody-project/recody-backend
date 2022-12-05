package com.recody.recodybackend.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PersonalizedMovieDetail implements PersonalizedContentDetail {
    
    private String title;
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
    private String releaseDate;
    private Integer runtime;
    
    private List<? extends Genre> genres;
    private String actors;
    private String directors;
}
