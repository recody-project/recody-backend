package com.recody.recodybackend.drama.features.getdramadetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetDramaDetail {
    
    private String dramaId;
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"GetDramaDetail\":{"
               + "\"dramaId\":" + ((dramaId != null) ? ("\"" + dramaId + "\"") : null)
               + ", \"locale\":" + locale
               + "}}";
    }
}
