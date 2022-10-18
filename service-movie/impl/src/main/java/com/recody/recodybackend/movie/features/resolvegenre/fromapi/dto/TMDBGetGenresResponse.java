package com.recody.recodybackend.movie.features.resolvegenre.fromapi.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TMDBGetGenresResponse {
    
    private List<TMDBGenre> genres;
    
}