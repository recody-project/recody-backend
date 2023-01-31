package com.recody.recodybackend.drama.features.fetchdramadetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchDramaDetail {
    
    private Integer tmdbId;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"FetchDramaDetail\":{"
               + "\"tmdbId\":" + tmdbId
               + ", \"locale\":" + locale
               + "}}";
    }
}
