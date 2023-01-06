package com.recody.recodybackend.drama;

public class DramaId {
    
    private final String value;
    
    private DramaId(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static DramaId of(String value) {
        return new DramaId( value );
    }
    
    @Override
    public String toString() {
        return "{\"DramaId\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
