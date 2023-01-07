package com.recody.recodybackend.drama.features.fetchdramacredit;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchDramaCredit {
    
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
