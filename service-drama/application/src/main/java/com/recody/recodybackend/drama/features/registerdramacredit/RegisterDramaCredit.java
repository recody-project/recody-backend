package com.recody.recodybackend.drama.features.registerdramacredit;

import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCreditResponse;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterDramaCredit {
    
    private Integer tmdbDramaId;
    private TMDBDramaCreditResponse response;
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"RegisterDramaCredit\":{"
               + "\"tmdbDramaId\":" + tmdbDramaId
               + ", \"response\":" + response
               + ", \"locale\":" + locale
               + "}}";
    }
}
