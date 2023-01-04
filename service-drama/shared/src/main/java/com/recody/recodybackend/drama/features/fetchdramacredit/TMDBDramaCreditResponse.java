package com.recody.recodybackend.drama.features.fetchdramacredit;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaCreditResponse {

    private List<TMDBDramaCast> cast;
    private List<TMDBDramaCrew> crew;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaCreditResponse\":{"
               + "\"cast\":" + cast
               + ", \"crew\":" + crew
               + "}}";
    }
}
