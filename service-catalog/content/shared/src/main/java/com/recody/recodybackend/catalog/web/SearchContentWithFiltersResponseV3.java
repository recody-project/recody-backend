package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.drama.Dramas;
import com.recody.recodybackend.movie.Movies;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchContentWithFiltersResponseV3 {
    
    private Movies movies;
    private Dramas dramas;
}
