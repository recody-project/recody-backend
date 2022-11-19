package com.recody.recodybackend.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class FailedToRemoveRecordException extends ApplicationException {
    
    public FailedToRemoveRecordException() {
        super(RecordErrorType.FailedToRemoveRecord, HttpStatus.BAD_REQUEST, "감상평을 삭제하는 데에 실패하였습니다.");
    }
}
