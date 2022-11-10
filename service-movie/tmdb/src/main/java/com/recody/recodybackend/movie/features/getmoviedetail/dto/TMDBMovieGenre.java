package com.recody.recodybackend.movie.features.getmoviedetail.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBMovieGenre {
    
    private Integer id;
    private String name;
    
    @Override
    public String toString() {
        return "{\"TMDBMovieGenre\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
