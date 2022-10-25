package com.recody.recodybackend.common.contents.movie;

import lombok.*;

import java.util.List;

/**
 * {@code List<Movie>} 의 Wrapper. <br>
 * 응답 바디에서의 반환 타입을 List 로 지정하기 어려울 때 사용할 수 있습니다.
 * <ul>
 *     <li>List.class 로 클래스 리터럴을 사용할 시 unchecked 경고를 받는 문제 해결</li>
 * </ul>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movies {
    
    private List<Movie> movies;
    
    @Override
    public String toString() {
        return "{\"Movies\":{" + "\"movies size\":" + movies.size() + "}}";
    }
}
