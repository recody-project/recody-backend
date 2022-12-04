package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

import java.util.List;

/**
 * 영화 상세정보의 ViewModel
 * <li> Actor, Director 를 ',' 로 묶어 String 으로 만든다. </li>
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetailViewModel {
    
    private String contentId;
    private String overview;
    private String releaseDate;
    private Integer runtime;
    private List<Genre> genres;
    private String actors;
    private String directors;
}
