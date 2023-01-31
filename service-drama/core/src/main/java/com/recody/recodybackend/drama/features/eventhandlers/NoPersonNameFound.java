package com.recody.recodybackend.drama.features.eventhandlers;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoPersonNameFound {
    
    private Long personId;
    
    @Override
    public String toString() {
        return "{\"NoPersonNameFound\":{"
               + "\"personId\":" + personId
               + "}}";
    }
}
