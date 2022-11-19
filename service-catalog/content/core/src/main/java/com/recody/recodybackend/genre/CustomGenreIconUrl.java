package com.recody.recodybackend.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value(staticConstructor = "of")
public class CustomGenreIconUrl {
    
    @JsonProperty("genreIconUrl")
    String value;
    
    CustomGenreIconUrl(String value) {
        validateIconUrl( value );
        this.value = value;
    }
    
    private static void validateIconUrl(String value){
        // 현재 로직 없음.
    }
    
    @Override
    public String toString() {
        return "{\"CustomGenreIconUrl\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
