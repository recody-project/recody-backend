package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.movie.Movie;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchContentWithFiltersResponse {
    
    public List<Movie> movies;
    // 영화 외 다른 컨텐츠 검색 기능 추가되면 그 결과들도 함께 반환한다.
    public Integer page;
    public Integer total;
}
