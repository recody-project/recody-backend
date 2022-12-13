package com.recody.recodybackend.movie.features.fetchmoviecredit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBGetMovieCreditResponse {
    
    public Integer id;
    public List<TMDBCast> cast;
    public List<TMDBCrew> crew;
    
    @Override
    public String toString() {
        return "{\"TMDBGetMovieCreditResponse\":{" + "\"id\":" + id + ", \"cast\":" + cast + ", \"crew\":" + crew + "}}";
    }
}
