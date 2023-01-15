package com.recody.recodybackend.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Author {

    @JsonIgnore
    private Long id;

    private String name;
    private String profilePath;

    @Override
    public String toString() {
        return "{\"Author\":{" + "\"id\":" + id + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + "}}";
    }
}
