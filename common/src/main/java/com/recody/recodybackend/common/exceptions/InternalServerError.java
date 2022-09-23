package com.recody.recodybackend.common.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerError extends ApplicationException {
    
    public InternalServerError(String message) {
        super(GlobalErrorType.InternalServerError,
              HttpStatus.INTERNAL_SERVER_ERROR,
              "정상적으로 처리하지 못했습니다. 다음 메시지를 개발자에게 알려주세요. \n" +
              message);
    }
    
    public InternalServerError() {
        super(GlobalErrorType.InternalServerError,
              HttpStatus.INTERNAL_SERVER_ERROR,
              "정상적으로 처리하지 못했습니다.");
    }
}
