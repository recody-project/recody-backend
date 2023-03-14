package com.recody.recodybackend.drama.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TMDBGetPersonDetailResponse {
    
    @JsonAlias(value = {"also_known_as"})
    private List<String> alsoKnownAs;
    
    private String name;
    
    @Override
    public String toString() {
        return "{\"TMDBGetPersonDetailResponse\":{"
               + "\"alsoKnownAs\":" + alsoKnownAs
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
