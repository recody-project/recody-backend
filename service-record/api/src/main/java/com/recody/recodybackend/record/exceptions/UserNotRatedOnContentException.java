package com.recody.recodybackend.record.exceptions;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotRatedOnContentException extends ApplicationException {
    
    public UserNotRatedOnContentException() {
        super(RecordErrorType.UserNotRatedOnContent, HttpStatus.BAD_REQUEST, "아직 작품에 별점을 남기지 않았습니다.");
    }
}
