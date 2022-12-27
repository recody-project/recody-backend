package com.recody.recodybackend.catalog.features.record.deleterecord;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.exceptions.RecordErrorType;
import org.springframework.http.HttpStatus;

class UserDoesNotOwnTheRecordException extends ApplicationException {
    
    public UserDoesNotOwnTheRecordException() {
        super(RecordErrorType.UserDoesNotOwnTheRecord, HttpStatus.FORBIDDEN, "해당 감상평은 유저에 소유권한이 없습니다.");
    }
}
