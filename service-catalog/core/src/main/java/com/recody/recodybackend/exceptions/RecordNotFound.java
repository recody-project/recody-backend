package com.recody.recodybackend.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class RecordNotFound extends ApplicationException {
    
    public RecordNotFound() {
        super(RecordErrorType.NoRecordFound, HttpStatus.NOT_FOUND, "감상평을 찾을 수 없습니다.");
    }
}
