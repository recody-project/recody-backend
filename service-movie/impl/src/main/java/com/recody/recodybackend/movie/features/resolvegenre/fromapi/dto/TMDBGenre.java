package com.recody.recodybackend.movie.features.resolvegenre.fromapi.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TMDBGenre {
    
    private Integer id;
    private String name;
}
