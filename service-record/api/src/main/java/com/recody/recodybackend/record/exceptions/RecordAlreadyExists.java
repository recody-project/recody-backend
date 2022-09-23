package com.recody.recodybackend.record.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class RecordAlreadyExists extends ApplicationException {
    
    public RecordAlreadyExists() {
        super(RecordErrorType.RecordAlreadyExists, HttpStatus.BAD_REQUEST, "이미 해당 작품에 감상평이 있습니다.");
    }
}
