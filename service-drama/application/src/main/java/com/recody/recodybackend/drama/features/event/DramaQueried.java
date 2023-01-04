package com.recody.recodybackend.drama.features.event;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaQueried {
    
    private String keyword;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"EmptyDramaQueried\":{"
               + "\"keyword\":" + ((keyword != null) ? ("\"" + keyword + "\"") : null)
               + "}}";
    }
}
