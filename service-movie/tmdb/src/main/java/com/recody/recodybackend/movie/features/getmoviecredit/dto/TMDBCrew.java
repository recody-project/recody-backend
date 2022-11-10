package com.recody.recodybackend.movie.features.getmoviecredit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TMDBCrew {
    
    private Integer id;
    private String name;
    private String originalName;
    @JsonAlias("known_for_department")
    private String knownForDepartment;
    
    @JsonAlias("profile_path")
    private String profilePath;
    
    private String department;
    
    private String job;
    
    @Override
    public String toString() {
        return "{\"TMDBCrew\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"originalName\":" + ((originalName != null) ? ("\"" + originalName + "\"") : null) + ", \"knownForDepartment\":" + ((knownForDepartment != null) ? ("\"" + knownForDepartment + "\"") : null) + ", \"department\":" + ((department != null) ? ("\"" + department + "\"") : null) + ", \"job\":" + ((job != null) ? ("\"" + job + "\"") : null) + "}}";
    }
}
