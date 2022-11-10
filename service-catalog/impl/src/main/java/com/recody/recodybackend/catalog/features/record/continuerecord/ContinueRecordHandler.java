package com.recody.recodybackend.catalog.features.record.continuerecord;

import com.recody.recodybackend.record.RecordEvent;

import java.util.List;

/**
 * 감상평 작성을 지속한다.
 * 받은 감상평 내용을 저장한다.
 * 완성된 상태라면 다시 기록중 상태로 변경한다.
 */
public interface ContinueRecordHandler {
    
    List<RecordEvent> handle(ContinueRecord command);
    
}
