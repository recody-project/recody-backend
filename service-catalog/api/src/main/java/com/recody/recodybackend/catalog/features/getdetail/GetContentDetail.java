package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetContentDetail {
    
    private Integer contentId;
    private String language;
    /**
     * 카테고리마다 로직을 처리할 핸들러를 나누면, 굳이 카테고리를 넘길 필요가 없다.
     * 또한, 카테고리마다 API 엔드포인트도 나눌 계획인데, 각각의 핸들러가 처리하면 된다.
     */
    @Deprecated
    private BasicCategory category;
    private Long userId;

}
