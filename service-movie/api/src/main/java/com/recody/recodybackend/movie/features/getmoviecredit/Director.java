package com.recody.recodybackend.movie.features.getmoviecredit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Director {
    @JsonIgnore // ID 세팅을 위한 쿼리가 많아 현재는 노출하지 않도록 하였음.
    private Long id;
    private String name;
    private String profilePath;
    
    @Override
    public String toString() {
        return "{\"Director\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null) + "}}";
    }
}
