package com.recody.recodybackend.record.features.deleterecord;

import java.time.LocalDateTime;

public interface DeleteRecordHandler {
    
    /**
     * 감상평을 삭제 처리한다.
     * @param command : recordId, userId
     * @return : 삭제된 시간
     */
    LocalDateTime handle(DeleteRecord command);
    
}
