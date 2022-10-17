package com.recody.recodybackend.movie.features.getmoviecredit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * tmdb 에서 한글로는 지원하지 않는다.
 * @author motive
 */
@Data
public class TMDBCast {

    private Integer id;
    private String name;
    
    private String character;
    
    @JsonAlias("cast_id")
    private Integer castId;
    
    @JsonAlias("credit_id")
    private String creditId;
    
    @JsonAlias("profile_path")
    private String profilePath;
    
    @JsonAlias("known_for_department")
    private String knownForDepartment;
    
    @Override
    public String toString() {
        return "{\"TMDBCast\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"character\":" + ((character != null) ? ("\"" + character + "\"") : null) + ", \"castId\":" + castId + ", \"creditId\":" + ((creditId != null) ? ("\"" + creditId + "\"") : null) + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null) + ", \"knownForDepartment\":" + ((knownForDepartment != null) ? ("\"" + knownForDepartment + "\"") : null) + "}}";
    }
}
