package com.recody.recodybackend.record.features.getrecordcontent;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetContinuingRecordContent {
    
    private Long userId;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"GetContinuingRecordContent\":{"
               + "\"userId\":" + userId
               + ", \"locale\":" + locale
               + "}}";
    }
}
