package com.recody.recodybackend.movie.features.applicationevent;

import lombok.*;

/**
 * 영화 검색 이벤트.
 * <li> 특정 키워드를 가지고 있어야 한다. </li>
 * <li> 검색 횟수가 많아지면 추가적으로 데이터를 가져온다. </li>
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieSearchRequested {
    
    private String keyword;
    
    @Override
    public String toString() {
        return "{\"MovieSearchRequested\":{"
               + "\"keyword\":" + ((keyword != null) ? ("\"" + keyword + "\"") : null)
               + "}}";
    }
}
