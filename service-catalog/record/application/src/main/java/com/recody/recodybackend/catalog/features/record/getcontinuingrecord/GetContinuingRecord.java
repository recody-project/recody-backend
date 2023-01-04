package com.recody.recodybackend.catalog.features.record.getcontinuingrecord;

import lombok.Builder;
import lombok.Data;

/**
 * 특정 유저의 가장 최근에 작성중인 감상평 정보를 가져온다.
 * */
@Data
@Builder
public class GetContinuingRecord {
    private Long userId;
}
