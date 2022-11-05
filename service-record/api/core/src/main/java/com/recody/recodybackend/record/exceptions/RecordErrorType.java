package com.recody.recodybackend.record.exceptions;

import com.recody.recodybackend.common.exceptions.ErrorType;

public enum RecordErrorType implements ErrorType {
    
    NoRecordFound("NoRecordFound"),
    RecordAlreadyExists("RecordAlreadyExists"),
    FailedToRemoveRecord("FailedToRemoveRecord"),
    UserDoesNotOwnTheRecord("UserDoesNotOwnTheRecord"),
    UserNotRatedOnContent("UserNotRatedOnContent"),
    
    RecordCountCannotBeNull("record.count.not-null"),
    
    RecordCountCannotBeNegative("record.count.not-negative"),
    
    ;
    
    private final String errorCode;
    
    RecordErrorType(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
