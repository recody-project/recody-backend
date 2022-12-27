package com.recody.recodybackend.movie.features.updatepersonname;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateKoreanPersonName {
    
    private Long personNameId;
    
    @Override
    public String toString() {
        return "{\"UpdateKoreanPersonName\":{"
               + "\"personNameId\":" + ((personNameId != null) ? ("\"" + personNameId + "\"") : null)
               + "}}";
    }
}
