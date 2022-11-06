package com.recody.recodybackend.record.features.getrecordcontents;


import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetRecordContents {
    
    private Long userId;
    private String categoryId;
    /**
     * 감상평 작성이 완성되었는지 여부로 필터링합니다.
     * null 이면 완성 여부와 상관없이 모두 쿼리합니다.
     */
    @Builder.Default
    private Boolean completed = null;
    private Integer size;
    private Integer page;
    
    private Locale locale;
    
    @Override
    public String toString() {
        return "{\"GetRecordContents\":{"
               + "\"userId\":" + userId
               + ", \"categoryId\":" + ((categoryId != null) ? ("\"" + categoryId + "\"") : null)
               + ", \"continuing\":" + completed
               + ", \"size\":" + size
               + ", \"page\":" + page
               + "}}";
    }
}
