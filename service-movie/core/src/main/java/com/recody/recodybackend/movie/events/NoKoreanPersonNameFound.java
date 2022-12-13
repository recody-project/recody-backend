package com.recody.recodybackend.movie.events;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoKoreanPersonNameFound {
    
    private Long personNameId;
    
    @Override
    public String toString() {
        return "{\"NoKoreanPersonNameFound\":{"
               + "\"personNameId\":" + personNameId
               + "}}";
    }
}
