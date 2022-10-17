package com.recody.recodybackend.movie.features.getmoviecredit;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class GetMovieCreditResult {
    
    private List<Actor> actors;
    private List<Director> directors;

}
