package com.recody.recodybackend.record.features.completerecord;

public interface CompleteRecordHandler {
    
    /**
     * 감상평 기록을 완료처리한다.
     * 포함된 감상평 내용도 업데이트 된다.
     *
     * returns: 감상평 완료 처리 여부
     */
    boolean handle(CompleteRecord command);
    
}
