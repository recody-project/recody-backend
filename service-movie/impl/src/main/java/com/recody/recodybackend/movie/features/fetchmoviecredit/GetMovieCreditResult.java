package com.recody.recodybackend.movie.features.fetchmoviecredit;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
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
